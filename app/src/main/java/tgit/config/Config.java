package tgit.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by liulixiang on 2014/12/24.
 */
public class Config {
    static String base_url_str = "BASE_URL";
    public static String BASE_URL = "http://172.16.222.52:8080/";
    public static final String URL_LOGIN = "login";
    public static final String INV_IN = "inventory/in";
    public static final String INV_OUT = "inventory/out";
    public static final String INV_DELIVERY = "inventory/createDelivery";
    public static final String INV_DELIVERY_ADD_ITEM="inventory/addDelivery";

    public static final String PRODUCT_SPLIT_URL = "product/split";

    public static final String APP_ID = "liulx.data_center";
    public static final String INV_TYPE = "inv_type";

    public static final String USER_INFO = "/userinfo/";

    public static String CurrentDeliveryNumber = "";

    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getBaseURL(Context context){
        return getSharedPreferences(context).getString(base_url_str, "");
    }

    public static void setBaseURL(Context context, String baseUrl){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(base_url_str, baseUrl);
        editor.commit();
        BASE_URL = baseUrl;
        System.out.println("BASE_URL:"+BASE_URL);
    }

    public static void initializeConfig(Context context){
        BASE_URL = getBaseURL(context);
    }

    public static String getUserInfo(String userId){
        return USER_INFO+userId;
    }

    public static String getItemsURL(String itemCode){
        return "product/"+itemCode;
    }

    public static String getItemURL(String itemId){
        return "product/item/"+itemId;
    }

    public static String getSplitURL(String itemId){return "product/split/"+itemId;}

    public static String getPrintURL(String itemId){return "product/print/"+itemId;}

    public static String getHtbUnfinished(){ return "product/htb"; }

    public static String getInvOutRemove(int itemId){return "inventory/deleteDelivery/"+itemId;}

    public static String getInvOutDeliveryDetails(String deliveryNumber){return "inventory/delivery/"+deliveryNumber;}
}
