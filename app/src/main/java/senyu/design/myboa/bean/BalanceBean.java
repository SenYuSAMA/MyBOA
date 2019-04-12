package senyu.design.myboa.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.Gson;

public class BalanceBean extends FinanceBean {
    @Override
    public String getPickerViewText() {
        return title;
    }

    public final static class ID {
        public static final int WECHAT_PAY = 4;
        public static final int ALI_PAY = 5;
        public static final int CREDIT_CARD = 2;
        public static final int CASH = 0;
        public static final int INVEST = 1;
        public static final int LENT = 3;
    }

        private int layoutCode;
        private String title;
        private int iconID;
        private int backgroundResID;
        private double amount;
        private String content;

    public BalanceBean(int layoutCode, String title, int iconID, int backgroundResID, double amount, String content) {
        this.layoutCode = layoutCode;
        this.title = title;
        this.iconID = iconID;
        this.backgroundResID = backgroundResID;
        this.amount = amount;
        this.content = content;
    }
    public BalanceBean(){

    }

    public int getLayoutCode() {
        return layoutCode;
    }
    public void setLayoutCode(int layoutCode) {
        this.layoutCode = layoutCode;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getIconID() {
        return iconID;
    }
    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
    public int getBackgroundResID() {
        return backgroundResID ;
    }
    public void setBackgroundResID(int backgroundResID) {
        this.backgroundResID = backgroundResID;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double computerAmount(Double value,boolean plusOrNot){
        if(plusOrNot){
            amount += value;
        }else{
            amount -= value;
        }
        return amount;
    }

}
