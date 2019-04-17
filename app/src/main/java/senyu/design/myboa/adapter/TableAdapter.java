package senyu.design.myboa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

import senyu.design.myboa.R;
import senyu.design.myboa.bean.Record;

public class TableAdapter extends BaseItemDraggableAdapter<Record, BaseViewHolder> {
    private Context mContext;
    public TableAdapter(List<Record> data,Context context) {
        super(R.layout.record_item,data);
        mContext = context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, Record item) {
        if(!item.isPlusOrNot()){
            helper.setText(R.id.type_tv,R.string.cost);
            helper.setText(R.id.cost_tv,"-"+String.valueOf(item.getCost())+"￥");
            helper.setTextColor(R.id.type_tv,mContext.getResources().getColor(R.color.pig_color_4));
            helper.setTextColor(R.id.cost_tv,mContext.getResources().getColor(R.color.pig_color_4));
        }else{
            helper.setText(R.id.type_tv,R.string.income);
            helper.setText(R.id.cost_tv,String.valueOf(item.getCost())+"￥");
            helper.setTextColor(R.id.type_tv,mContext.getResources().getColor(R.color.pig_color_1));
            helper.setTextColor(R.id.cost_tv,mContext.getResources().getColor(R.color.pig_color_1));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日hh时mm分");
        String text = simpleDateFormat.format(item.getDate());
        helper.setText(R.id.time_tv,text)
                .setText(R.id.by_title_tv,item.getByTitle())
                .setText(R.id.usage_tv,item.getUsage());

    }
}
