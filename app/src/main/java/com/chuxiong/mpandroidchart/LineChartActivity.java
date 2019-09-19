package com.chuxiong.mpandroidchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chuxiong.mpandroidchart.model.IncomeBean;
import com.chuxiong.mpandroidchart.model.LineChartBean;
import com.chuxiong.mpandroidchart.util.LocalJsonAnalyzeUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private LineChart mLineChart;
    //X 轴
    private XAxis mXAxis;
    //左边 Y 轴
    private YAxis mAxisLeft;
    //右边 Y 轴
    private YAxis mAxisRight;
    //图例
    private Legend mLegend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        initView();
        initData();
        initListener();
        LineChartBean lineChartBean = LocalJsonAnalyzeUtil.jsonToObject(this,
                "line_char.json", LineChartBean.class);
        List<IncomeBean> list = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();
        showLineChart(list, "我的收益", Color.CYAN);
    }

    public void initView() {
        mLineChart = findViewById(R.id.lineChar1);
        /***图表设置***/
        {
            //是否显示边界
            mLineChart.setDrawBorders(true);
            //是否展示网格线
            mLineChart.setDrawGridBackground(false);
            //是否可以拖动
            mLineChart.setDragEnabled(false);
            //是否有触摸事件
            mLineChart.setTouchEnabled(false);
            //设置XY轴动画效果
            mLineChart.animateY(2500);
            mLineChart.animateX(1500);

        }

        /***X/Y 轴设置***/
        {
            //获取 X 轴
            mXAxis = mLineChart.getXAxis();
            //获取左边 Y 轴
            mAxisLeft = mLineChart.getAxisLeft();
            //获取右边 Y 轴
            mAxisRight = mLineChart.getAxisRight();
            //设置 X 轴显示位置，默认顶部
            mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            //设置X轴的最小值
            mXAxis.setAxisMinimum(0f);
            //设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
            mXAxis.setGranularity(1f);

        }
        /***折线图例 标签 设置***/
        {   //获得图例
            mLegend = mLineChart.getLegend();
            //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
            mLegend.setForm(Legend.LegendForm.LINE);
            //设置图例文字大小
            mLegend.setTextSize(12f);
            //显示位置 左下方
            mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            //是否绘制在图表里面
            mLegend.setDrawInside(false);
        }


    }

    public void initData() {

    }

    public void initListener() {

    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        //设置数据集线条颜色
        lineDataSet.setColor(color);
        //设置数集点颜色
        lineDataSet.setCircleColor(color);
        //设置数集线条宽度
        lineDataSet.setLineWidth(1f);
        //设置数集点弧度
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        //设置数据集文字大小
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        //图例 线宽
        lineDataSet.setFormLineWidth(1f);
        //图例 线长
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(List<IncomeBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            IncomeBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
    }
}
