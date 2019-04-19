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
import senyu.design.myboa.utils.DateUtils;
import senyu.design.myboa.utils.SPUtils;


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
    }
}
