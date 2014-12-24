package tgit.inventory.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.net.RestClient;
import tgit.util.UIHelper;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText edtUserName, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        if (TextUtils.isEmpty(edtUserName.getText())|| TextUtils.isEmpty(edtPassword.getText())){
            UIHelper.ToastMessage(this, "用户名和密码都不能为空");
            return;
        }

        RequestParams params = new RequestParams();
        params.put("username", edtUserName.getText().toString());
        params.put("password", edtPassword.getText().toString());

        RestClient.post(Config.URL_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    String token = response.getString("token");
                    System.out.println("返回的令牌：" + token);
                    RestClient.getClient().addHeader("Authorization", "Token " + token);
                    UIHelper.ToastMessage(LoginActivity.this, "登录成功");
                    Intent i = new Intent(LoginActivity.this, InvInActivity.class);
                    int inv_type = LoginActivity.this.getIntent().getIntExtra(Config.INV_TYPE, 0);
                    i.putExtra(Config.INV_TYPE, inv_type);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    System.out.println("A发生错误：" + e.toString());
                    UIHelper.ToastMessage(LoginActivity.this, e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("发生错误3" + throwable.getMessage());
                for (Header header : headers) {
                    System.out.println(header.getName() + ":" + header.getValue());
                }
                UIHelper.ToastMessage(LoginActivity.this, throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                System.out.println("发生错误4+status code:" + statusCode);
                for (Header header : headers) {
                    System.out.println(header.getName() + ":" + header.getValue());
                }
                System.out.println(responseString);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
