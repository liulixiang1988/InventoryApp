package tgit.inventory.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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
    private EditText edtDeliveryNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_out);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        edtDeliveryNumber = (EditText) findViewById(R.id.edtDeliveryNumber);

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
                createDelivery();
                break;
        }
    }

    private void createDelivery(){
        Log.v(TAG, "发送按钮点击");
        if(TextUtils.isEmpty(getDeliveryNumber())){
            UIHelper.alert(this,"错误", "挑库编码不能为空");
            return;
        }
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("DeliveryNumber", getDeliveryNumber());
            Log.v(TAG, jsonParams.toString());
            StringEntity entity = new StringEntity(jsonParams.toString());
            RestClient.postJson(this, Config.INV_DELIVERY, entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    try {
                        String msg = response.getString("msg");
                        Log.v(TAG, "返回的消息：" + msg);
                        UIHelper.toastMessage(InvOutActivity.this, msg);
                        int resultCode = response.getInt("status");
                        if (resultCode == 1) {
                            Intent i = new Intent(InvOutActivity.this, InvOutDeliveryListActivity.class);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "A发生错误：" + e.toString());
                        UIHelper.toastMessage(InvOutActivity.this, e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    Log.e(TAG, "发生错误3:" + throwable.getMessage());
                    UIHelper.toastMessage(InvOutActivity.this, "发生错误3 状态：" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      String responseString, Throwable throwable) {
                    Log.e(TAG, "发生错误4:" + throwable.getMessage());
                }
            });
        }
        catch (JSONException e) {
            Log.e(TAG, "B发生错误："+e.toString());
            UIHelper.toastMessage(InvOutActivity.this, e.toString());
        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, "C发生错误："+e.toString());
            UIHelper.toastMessage(InvOutActivity.this, e.toString());
        }
        catch(Exception e){
            Log.e(TAG, "D发生错误" + e.toString());
        }
        Log.v(TAG, "发送结束");
    }

    public String getDeliveryNumber(){
        return edtDeliveryNumber.getText().toString();
    }

    private void clearText() {
        edtDeliveryNumber.getText().clear();
    }
}
