package tgit.inventory.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.MainActivity;
import tgit.inventory.app.R;
import tgit.model.User;
import tgit.net.RestClient;
import tgit.session.CurrentUser;
import tgit.util.UIHelper;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtUserName, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
            Log.v(TAG, "隐藏登录界面的状态栏");
        }else{
            Log.v(TAG, "Login不存在ActionBar");
        }

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final String userId = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (userId.isEmpty() || password.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle(R.string.app_name)
                    .setMessage(R.string.login_error_text)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("username", userId);
        params.put("password", password);

        RestClient.post(Config.URL_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    String token = response.getString("token");
                    Log.v(TAG, "返回的令牌：" + token);
                    RestClient.getClient().addHeader("Authorization", "Token " + token);
                    UIHelper.toastMessage(LoginActivity.this, "登录成功");

                    Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                    String resultJson = response.getJSONObject("userinfo").toString();
                    Log.v(TAG, resultJson);
                    User user = g.fromJson(resultJson, User.class);
                    CurrentUser.setCurrentUser(user);

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } catch (JSONException e) {
                    Log.v(TAG, "A发生错误：" + e.toString());
                    UIHelper.toastMessage(LoginActivity.this, e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.v(TAG, "发生错误3" + throwable.getMessage());
                for (Header header : headers) {
                    Log.v(TAG, header.getName() + ":" + header.getValue());
                }
                UIHelper.toastMessage(LoginActivity.this, throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                Log.v(TAG, "发生错误4+status code:" + statusCode);
                for (Header header : headers) {
                    Log.v(TAG, header.getName() + ":" + header.getValue());
                }
                Log.v(TAG, responseString);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
