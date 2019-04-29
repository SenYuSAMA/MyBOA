package senyu.design.myboa.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Response;
import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.bean.OweBean;
import senyu.design.myboa.bean.Record;

public class JSONUtil {

    public static List<BalanceBean> parseBalanceJSON(String response){
        List<BalanceBean> resultList = new ArrayList<>();
        if(!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = (JSONObject)new JSONObject(response).get("params");
                JSONArray datas = jsonObject.getJSONArray("balance");
                for(int i = 0;i < datas.length();i++){
                    JSONObject balanceJO = datas.getJSONObject(i);
                    BalanceBean bean = new BalanceBean();
                    bean.setTitle(balanceJO.getString("title"));
                    bean.setLayoutCode(balanceJO.getInt("layoutcode"));
                    bean.setAmount(balanceJO.getDouble("amount"));
                    bean.setIconID(balanceJO.getInt("iconid"));
                    bean.setBackgroundResID(balanceJO.getInt("backgroundresid"));
                    bean.setContent(balanceJO.getString("content"));
                    resultList.add(bean);
                }
                return resultList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    public static List<OweBean> parseOweJSON(String response){
        List<OweBean> resultList = new ArrayList<>();
        if(!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = (JSONObject)new JSONObject(response).get("params");
                JSONArray datas = jsonObject.getJSONArray("owe");
                for(int i = 0;i < datas.length();i++){
                    JSONObject balanceJO = datas.getJSONObject(i);
                    OweBean bean = new OweBean();
                    bean.setTitle(balanceJO.getString("title"));
                    bean.setLayoutCode(balanceJO.getInt("layoutcode"));
                    bean.setAmount(balanceJO.getDouble("amount"));
                    bean.setIconID(balanceJO.getInt("iconid"));
                    bean.setBackgroundResID(balanceJO.getInt("backgroundresid"));
                    bean.setContent(balanceJO.getString("content"));
                    resultList.add(bean);
                }
                return resultList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    public static List<Record> parseRecordJSON(String response){
        List<Record> resultList = new ArrayList<>();
        if(!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = (JSONObject)new JSONObject(response).get("params");
                JSONArray datas = jsonObject.getJSONArray("record");
                for(int i = 0;i < datas.length();i++){
                    JSONObject balanceJO = datas.getJSONObject(i);
                    Record bean = new Record();
                    bean.setCost(balanceJO.getDouble("cost"));
                    bean.setUsage(balanceJO.getString("usage"));
                    bean.setPlusOrNot(balanceJO.getInt("isplusornot")==1);
                    bean.setByTitle(balanceJO.getString("bytitle"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse((balanceJO.getString("date")));
                    bean.setDate(date);
                    resultList.add(bean);
                }
                return resultList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }
        public static boolean parseRegisterJSON(String resposne){
            if(!TextUtils.isEmpty(resposne)){
                try {
                    JSONObject jsonObject = (JSONObject)new JSONObject(resposne).get("params");
                    if("success".equals(jsonObject.getString("Result"))){
                        return true;
                    }else{
                        return false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
}
