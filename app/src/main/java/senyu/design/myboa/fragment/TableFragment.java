package senyu.design.myboa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import senyu.design.myboa.R;
import senyu.design.myboa.adapter.OweRecyclerViewAdapter;
import senyu.design.myboa.adapter.TableAdapter;
import senyu.design.myboa.bean.Record;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TableAdapter mAdapter;
    private List<Record> mDatas;

    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //todo 完善本fragment的界面，并且拿到数据展示
        View view =inflater.inflate(R.layout.fragment_table, container, false);
        mRecyclerView = view.findViewById(R.id.table_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        initData();
        mAdapter = new TableAdapter(mDatas,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new Record(5.43,"2014 15 15","蚂蚁华北","吃",true));
    }

    public void upDate(Record data){
        mDatas.add(data);
        mAdapter.notifyDataSetChanged();
    }
}
