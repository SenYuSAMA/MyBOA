package senyu.design.myboa.bean;

public class Account  {
    public static final int WECHAT_PAY = 0;
    public static final int ALI_PAY = 1;
    public static final int CREDIT_CARD = 2;
    public static final int CASH = 3;
    public static final int INVEST = 4;
    public static final int LENT = 5;

    public int getLayoutCode() {
        return layoutCode;
    }

    public void setLayoutCode(int layoutCode) {
        this.layoutCode = layoutCode;
    }

    private int layoutCode;
    private String title;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private double amount;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
