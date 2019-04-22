package senyu.design.myboa.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import senyu.design.myboa.bean.Record;

public class DateUtils {
    public static void sortByDate(List<Record> data){
        Collections.sort(data, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                int flag = o1.getDate().compareTo(o2.getDate());
                return flag;
            }
        });
    }

  /*  public static String[] getXAxis(List<Record> datas){
        return null;
    }

    public static float getYAxis(){

    }
*/
}
