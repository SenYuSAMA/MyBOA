package senyu.design.myboa.utils;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpJSONPost(String url,String jsonStr,Callback callback){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("text/json;charset:utf-8");
        RequestBody body = RequestBody.create(JSON,jsonStr);
        Log.d("tttest",body.toString());
        Log.d("tttest",jsonStr);
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(callback);
    }

}
