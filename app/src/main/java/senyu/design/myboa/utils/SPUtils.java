package senyu.design.myboa.utils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import senyu.design.myboa.bean.BalanceBean;
import senyu.design.myboa.bean.FinanceBean;
import senyu.design.myboa.bean.OweBean;
import senyu.design.myboa.bean.Record;

public class SPUtils
{
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";
    public static final String TOTAL_OWE = "totalOwe";
    public static final String TOTAL_BALANCE = "totalBalance";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }
    public static final String FIRST_COME = "first_come";
    public static final String BALANCE_BEAN_KEY = "BalanceBean";
    public static final String OWE_BEAN_KEY = "OweBean";
    public static final String RECORD = "Record";

public static boolean  saveBeantoSP(List<BalanceBean> datas,Context context){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(datas);
        put(context,BALANCE_BEAN_KEY,jsonStr);
        return true;
    }

    public static List<BalanceBean> getBeanFromSP(Context context,String key,String defaultStr){
        String jsonStr = (String)get(context,key,defaultStr);
        if(jsonStr != null){
            Gson gson = new Gson();
            List<BalanceBean> datas = gson.fromJson(jsonStr,new TypeToken<List<BalanceBean>>(){}.getType());
            return datas;
        }
        return null;
    }
    public static boolean  saveOweToSP(List<OweBean> datas, Context context){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(datas);
        put(context,OWE_BEAN_KEY,jsonStr);
        return true;
    }

    public static List<OweBean> getOweFromSP(Context context,String key,String defaultStr){
        String jsonStr = (String)get(context,key,defaultStr);
        if(jsonStr != null){
            Gson gson = new Gson();
            List<OweBean> datas = gson.fromJson(jsonStr,new TypeToken<List<OweBean>>(){}.getType());
            return datas;
        }
        return null;
    }

    public static List<FinanceBean> getFinanceBean(Context context){
            List<OweBean> oweList = getOweFromSP(context,SPUtils.OWE_BEAN_KEY,"");
            List<BalanceBean> balanceList = getBeanFromSP(context,SPUtils.BALANCE_BEAN_KEY,"");
            List<FinanceBean> result = new ArrayList<>();
            if(balanceList != null) {
                result.addAll(balanceList);
            }
        if (oweList != null) {
            result.addAll(oweList);
        }
            return  result;
        }

    public static List<Record> getRecordFromSP(Context context,String key,String defaultStr){
        String jsonStr = (String)get(context,key,defaultStr);
        if(jsonStr != null){
            Gson gson = new Gson();
            List<Record> datas = gson.fromJson(jsonStr,new TypeToken<List<Record>>(){}.getType());
            return datas;
        }
        return null;
    }

    public static boolean  saveRecordToSP(List<Record> datas, Context context){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(datas);
        put(context,RECORD,jsonStr);
        return true;
    }

}