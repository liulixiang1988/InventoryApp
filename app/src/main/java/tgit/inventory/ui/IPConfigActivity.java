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
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.util.UIHelper;

public class IPConfigActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = ActionBarActivity.class.getSimpleName();

    private EditText edtIpAddress;

    public String getIpAddress(){
        return edtIpAddress.getText().toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipconfig);

        Button btnSaveIpConfig = (Button) findViewById(R.id.btnSaveIpConfig);

        edtIpAddress = (EditText) findViewById(R.id.edtIpAddress);

        btnSaveIpConfig.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ipconfig, menu);
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
            case R.id.btnSaveIpConfig:
                btnSaveIpConfig_click();
        }
    }

    private void btnSaveIpConfig_click(){
        if(TextUtils.isEmpty(edtIpAddress.getText())){
            UIHelper.toastMessage(this, "IP地址不能为空!");
        }

        Log.v(TAG, "保存IP为：" + getIpAddress());
        Config.setBaseURL(this, getIpAddress());
        UIHelper.toastMessage(this, "IP保存成功");
        finish();
    }
}
