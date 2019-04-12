package senyu.design.myboa.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.List;

import senyu.design.myboa.R;
import senyu.design.myboa.bean.Account;

public class MyAdapter extends BaseItemDraggableAdapter<Account,BaseViewHolder> {


    public MyAdapter( @Nullable List<Account> data) {

        super(R.layout.balance_item, data);
    }

    public MyAdapter(){
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<Account>() {
            @Override
            protected int getItemType(Account account) {
                return account.getLayoutCode();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(Account.WECHAT_PAY,R.layout.wechat_item)
                .registerItemType(Account.ALI_PAY,R.layout.alipay_item)
                .registerItemType(Account.CASH,R.layout.cash_item)
                .registerItemType(Account.LENT,R.layout.lent_item)
                .registerItemType(Account.INVEST,R.layout.invest_item)
                .registerItemType(Account.CREDIT_CARD,R.layout.credit_card_item);

    }

    @Override
    protected void convert(BaseViewHolder helper, Account item) {

        switch (helper.getItemViewType()) {

        }
    }
}
