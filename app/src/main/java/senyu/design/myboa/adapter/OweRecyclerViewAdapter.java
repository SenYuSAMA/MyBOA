package senyu.design.myboa.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import senyu.design.myboa.R;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.bean.OweBean;

public class OweRecyclerViewAdapter extends BaseItemDraggableAdapter<OweBean, BaseViewHolder> {

    public OweRecyclerViewAdapter(List<OweBean> data){
        super(R.layout.common_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OweBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setBackgroundRes(R.id.large_bg,item.getBackgroundResID())
                .setImageResource(R.id.iv_img,item.getIconID())
                .setText(R.id.tv_content,item.getContent())
                .setText(R.id.amount,String.valueOf(item.getAmount()))
                .addOnClickListener(R.id.amount);
    }
}
