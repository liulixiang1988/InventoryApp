package tgit.handler;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONObject;
import tgit.model.User;
import tgit.session.CurrentUser;

/**
 * Created by liulixiang on 2014/12/25.
 */
public class UserInfoRequestHandler extends JsonHttpResponseHandler {
    public static final String TAG = UserInfoRequestHandler.class.getSimpleName();
    @Override
    public void onSuccess(int statusCode, Header[] headers,
                          JSONObject response) {
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String resultJson = response.toString();
        Log.v(TAG, resultJson);
        User user = g.fromJson(resultJson, User.class);
        CurrentUser.setCurrentUser(user);
    }
}
