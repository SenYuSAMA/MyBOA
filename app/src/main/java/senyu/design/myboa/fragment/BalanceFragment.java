package senyu.design.myboa.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.List;

import senyu.design.myboa.MyItemDragAndSwipeCallback;
import senyu.design.myboa.R;
import senyu.design.myboa.adapter.MyAdapter;
import senyu.design.myboa.bean.Account;


public class BalanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Account> datas;
    private MyAdapter adapter;

    public BalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        recyclerView = view.findViewById(R.id.balance_rcv);
        datas = new ArrayList<>();
        Account account;
       for(int i = 0 ;i < 21;i++){
           account = new Account();
          if(i % 3 == 0 && i % 6 != 0){
              account.setLayoutCode(Account.ALI_PAY);
          }else if(i % 2 == 0 && i % 4 !=0 && i % 6 !=0){
              account.setLayoutCode(Account.WECHAT_PAY);
          }else if (i% 4 == 0){
              account.setLayoutCode(Account.CASH);
          }else if (i % 5 ==0){
              account.setLayoutCode(Account.CREDIT_CARD);
          }else if(i % 6 == 0){
              account.setLayoutCode(Account.INVEST);
          }else if(i % 7 == 0){
              account.setLayoutCode(Account.LENT);
          }
           datas.add(account);
       }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter();
        adapter.addData(datas);



        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new MyItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.enableDragItem(itemTouchHelper,R.id.iv_img,true);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        OnItemDragListener onItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){}
            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {}
            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {}
        };
        adapter.setOnItemDragListener(onItemDragListener);



        recyclerView.setAdapter(adapter);
        return view;
    }

}
