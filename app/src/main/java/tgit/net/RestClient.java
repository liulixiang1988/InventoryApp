package tgit.net;

import android.content.Context;
import android.util.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import org.apache.http.HttpEntity;
import tgit.config.Config;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by liulixiang on 2014/12/24.
 */
public class RestClient {

    public final static String TAG = RestClient.class.getSimpleName();

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.addHeader("Accept", "*/*");
        client.setUserAgent("data-center-client");
    }

    public static AsyncHttpClient getClient(){
        return client;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        Log.v(TAG, "请求Url:"+getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postJson(Context context, String url, HttpEntity entity,  ResponseHandlerInterface responseHandler){
        Log.v(TAG, "请求Url:" + getAbsoluteUrl(url));
        String contentType = "application/json";
        client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Config.BASE_URL + relativeUrl;
    }

}