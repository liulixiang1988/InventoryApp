package tgit.inventory.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class InvInActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = InvInActivity.class.getSimpleName();

    private EditText edtItemCode, edtInvCode;
    private TextView txtItemCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_in);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        Button btnClear = (Button) findViewById(R.id.btnClear);

        edtItemCode = (EditText) findViewById(R.id.edtItemCode);
        edtItemCode.setInputType(InputType.TYPE_NULL);
        edtInvCode = (EditText) findViewById(R.id.edtInvCode);
        edtInvCode.setInputType(InputType.TYPE_NULL);

        txtItemCodes = (TextView) findViewById(R.id.txtItemCodes);

        btnAddItem.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //清空
                txtItemCodes.setText("");
                edtInvCode.setText("");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inv_in, menu);
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
            case R.id.btnAddItem:
                btnAddItem_onClick();
                break;
            case R.id.btnSend:
                btnSend_onClick();
                break;
            default:
                break;
        }
    }

    //添加物料
    private void btnAddItem_onClick(){
        txtItemCodes.append(getItemCode()+"\n");
        edtItemCode.setText("");
        edtItemCode.requestFocus();
    }

    //发送数据
    private void btnSend_onClick(){
        Log.v(TAG, "发送按钮点击");
        if(TextUtils.isEmpty(getItemsCode()) || TextUtils.isEmpty(edtInvCode.getText())){
            UIHelper.toastMessage(this, "物料条码和货位条码不能为空");
            return;
        }
        JSONObject jsonParams = new JSONObject();
        try {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "入库", "正在保存，请稍后", true, true);
            jsonParams.put("items_code", getItemsCode());
            jsonParams.put("inventory_code", getInvCode());
            Log.v(TAG, jsonParams.toString());
            StringEntity entity = new StringEntity(jsonParams.toString());
            RestClient.postJson(this, Config.INV_IN, entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String msg = response.getString("msg");
                        Log.v(TAG, "返回的消息：" + msg);
                        UIHelper.toastMessage(InvInActivity.this, msg);
                        int resultCode = response.getInt("status");
                        if (resultCode == 1) {
                            clearText();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "A发生错误：" + e.toString());
                        UIHelper.toastMessage(InvInActivity.this, e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Log.e(TAG, "发生错误3 Statuscode:" + statusCode + " ");
                    UIHelper.toastMessage(InvInActivity.this, "发生错误3 状态：" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      String responseString, Throwable throwable) {
                    progressDialog.dismiss();
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
            UIHelper.toastMessage(InvInActivity.this, e.toString());
        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, "C发生错误："+e.toString());
            UIHelper.toastMessage(InvInActivity.this, e.toString());
        }
        catch(Exception e){
            Log.e(TAG, "D发生错误" + e.toString());
        }
        Log.v(TAG, "发送结束");
    }

    private void clearText(){
        edtItemCode.getText().clear();
        edtItemCode.requestFocus();
        txtItemCodes.setText("");
    }

    public String getItemCode(){
        return edtItemCode.getText().toString();
    }

    public String getInvCode(){
        return edtInvCode.getText().toString();
    }

    public String getItemsCode(){
        return txtItemCodes.getText().toString().replace('\n', ';');
    }

}
