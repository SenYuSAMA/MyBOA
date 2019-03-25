package senyu.design.myboa.bean;

public class OweBean {

    public final static class ID {
        public static final int HUA_BEI = 0;
        public static final int CREDIT_OWE = 1;
        public static final int JD_BAITIAO = 2;
        public static final int OWE_BILL = 3;
    }

    private int layoutCode;
    private String title;
    private int iconID;
    private int backgroundResID;
    private double amount;
    private String content;

    public OweBean(int layoutCode, String title, int iconID, int backgroundResID, double amount, String content) {
        this.layoutCode = layoutCode;
        this.title = title;
        this.iconID = iconID;
        this.backgroundResID = backgroundResID;
        this.amount = amount;
        this.content = content;
    }
    public OweBean(){

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
}
