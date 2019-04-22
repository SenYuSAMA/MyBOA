package senyu.design.myboa.bean;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record implements Serializable {
    private static final long serialVersionUID = 311;
    private double cost;
    private Date date;
    private String byTitle;
    private String usage;
    private boolean plusOrNot;


    public Record(double cost, Date date, String byTitle, String usage, boolean plusOrNot) {
        this.cost = cost;
        this.date = date;
        this.byTitle = byTitle;
        this.usage = usage;
        this.plusOrNot = plusOrNot;
    }
    public Record(){

    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

public String getByTitle() {
        return byTitle;
    }

    public void setByTitle(String byTitle) {
        this.byTitle = byTitle;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public boolean isPlusOrNot() {
        return plusOrNot;
    }

    public void setPlusOrNot(boolean plusOrNot) {
        this.plusOrNot = plusOrNot;
    }

    @Override
    public String toString() {
        return date + String.valueOf(cost) + byTitle + usage + plusOrNot;
    }

    public String getDateString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String result = simpleDateFormat.format(date);
        return result.substring(5,11);
    }
    
}
