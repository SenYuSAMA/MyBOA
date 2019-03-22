package senyu.design.myboa.bean;

public class Account  {
    public static final int WECHAT_PAY = 4;
    public static final int ALI_PAY = 5;
    public static final int CREDIT_CARD = 2;
    public static final int CASH = 0;
    public static final int INVEST = 1;
    public static final int LENT = 3;

    private int layoutCode;
    private String title;
    private int iconID;
    private int backGroundColorId;
    private double amount;


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


}
