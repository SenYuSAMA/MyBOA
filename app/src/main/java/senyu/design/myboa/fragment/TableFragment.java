package senyu.design.myboa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import senyu.design.myboa.R;
import senyu.design.myboa.adapter.OweRecyclerViewAdapter;
import senyu.design.myboa.adapter.TableAdapter;
import senyu.design.myboa.bean.Record;
import senyu.design.myboa.utils.DateUtils;
import senyu.design.myboa.utils.SPUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TableAdapter mAdapter;
    private List<Record> mDatas;
    private LineChart mLineChart;

    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_table, container, false);
        mRecyclerView = view.findViewById(R.id.table_recyclerview);
        mLineChart = view.findViewById(R.id.line_chart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        initData();
        initChart();
        mAdapter = new TableAdapter(mDatas,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }



    private void initData() {
        if(SPUtils.getRecordFromSP(getActivity(),SPUtils.RECORD,"")!=null) {
            mDatas = SPUtils.getRecordFromSP(getActivity(), SPUtils.RECORD, "");
        }else{
            mDatas = new ArrayList<>();
        }
    }

    public void upDate(Record data){
        mDatas.add(data);
        DateUtils.sortByDate(mDatas);
        SPUtils.saveRecordToSP(mDatas,getActivity());
        mAdapter.notifyDataSetChanged();
        updateChart(data);
    }

    private void updateChart(Record record) {
        initChart();
       /* LineData data = mLineChart.getLineData();
        if(record.isPlusOrNot()){
            LineDataSet dataSet = (LineDataSet) data.getDataSetByIndex(0);
            Entry entry = new Entry(dataSet.getEntryCount(),(float)record.getCost());
            data.addEntry(entry,0);
        }else{
            LineDataSet dataSet = (LineDataSet) data.getDataSetByIndex(1);
            Entry entry = new Entry(dataSet.getEntryCount(),(float)record.getCost());
            data.addEntry(entry,1);
        }
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();*/
    }

    private void initChart() {
        //设置描述
        mLineChart.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText("收支折线图");
        mLineChart.setDescription(description);
        //设置按比例缩放图
        mLineChart.setPinchZoom(true);
        //x轴设置
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(15);
        xAxis.setAxisLineWidth(2f);
        //设置x轴的自定义格式
/*        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter();
        String[] strings = new String[mDatas.size()];
        for(int i = 0 ;i <mDatas.size();i++){
           *//* if(i <mDatas.size()-1 && mDatas.get(i).getDateString().equals(mDatas.get(i+1).getDateString())) {*//*
                strings[i] = mDatas.get(i).getDateString() + "'";
           *//* }*//*
        }
        formatter.setValues(strings);
        xAxis.setValueFormatter(formatter);*/
        //y轴设置
        YAxis yAxis = mLineChart.getAxisLeft();
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);
        //图例设置
        Legend legend = mLineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        mLineChart.setExtraOffsets(10, 30, 20, 10);
        mLineChart.animateX(1500);
        //设置数据
        List<Entry> entriesPos = new ArrayList<>();
        List<Entry> entriesNag = new ArrayList<>();
        for(int i = 0;i < mDatas.size();i++){
            if(mDatas.get(i).isPlusOrNot()){
                entriesPos.add(new Entry(i,(float)(mDatas.get(i).getCost())));
            }else{
                entriesNag.add(new Entry(i,(float)(mDatas.get(i).getCost())));
            }
        }
        LineDataSet dataSetNag = new LineDataSet(entriesNag,"消费");
        LineDataSet dataSetPos = new LineDataSet(entriesPos,"收入");
        dataSetNag.setValueTextColor(getResources().getColor(R.color.red));
        dataSetNag.setColor(getResources().getColor(R.color.red));
        dataSetPos.setValueTextColor(getResources().getColor(R.color.green));
        dataSetPos.setColor(getResources().getColor(R.color.green));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetPos);
        dataSets.add(dataSetNag);
        LineData lineData = new LineData(dataSets);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }

}
