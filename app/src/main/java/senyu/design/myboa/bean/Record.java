package senyu.design.myboa.bean;


import java.io.Serializable;

public class Record implements Serializable {

    private double cost;
    private String date;
    private String byTitle;
    private String usage;
    private boolean plusOrNot;


    public Record(double cost, String date, String byTitle, String usage, boolean plusOrNot) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
