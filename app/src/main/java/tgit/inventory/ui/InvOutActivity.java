package tgit.inventory.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.io.UnsupportedEncodingException;

public class InvOutActivity extends ActionBarActivity implements View.OnClickListener {


    public final static String TAG = InvOutActivity.class.getSimpleName();
    private EditText edtItemCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_out);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        edtItemCode = (EditText) findViewById(R.id.edtItemCode);

        btnSave.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inv_out, menu);
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
        switch(v.getId()){
            case R.id.btnSave:
                saveItemCode();
                break;
        }
    }

    private void saveItemCode(){
        Log.v(TAG, "发送按钮点击");
        if(TextUtils.isEmpty(getItemCode())){
            UIHelper.ToastMessage(this, "物料条码不能为空");
            return;
        }
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("item_code", getItemCode());
            Log.v(TAG, jsonParams.toString());
            StringEntity entity = new StringEntity(jsonParams.toString());
            RestClient.postJson(this, Config.INV_OUT, entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    try {
                        String msg = response.getString("msg");
                        Log.v(TAG, "返回的消息：" + msg);
                        UIHelper.ToastMessage(InvOutActivity.this, msg);
                        int resultCode = response.getInt("status");
                        if (resultCode == 1) {
                            clearText();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "A发生错误：" + e.toString());
                        UIHelper.ToastMessage(InvOutActivity.this, e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    Log.e(TAG, "发生错误3 Statuscode:" + statusCode + " ");
                    UIHelper.ToastMessage(InvOutActivity.this, "发生错误3 状态：" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      String responseString, Throwable throwable) {
                    Log.e(TAG, "发生错误4+status code:" + statusCode);
                    for (Header header : headers) {
                        Log.e(TAG, header.getName() + ":" + header.getValue());
                    }
                    Log.e(TAG, responseString);
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        catch (JSONException e) {
            Log.e(TAG, "B发生错误："+e.toString());
            UIHelper.ToastMessage(InvOutActivity.this, e.toString());
        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, "C发生错误："+e.toString());
            UIHelper.ToastMessage(InvOutActivity.this, e.toString());
        }
        catch(Exception e){
            Log.e(TAG, "D发生错误" + e.toString());
        }
        Log.v(TAG, "发送结束");
    }

    public String getItemCode(){
        return edtItemCode.getText().toString();
    }

    private void clearText() {
        edtItemCode.getText().clear();
    }
}
