package tgit.inventory.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import tgit.config.Constants;
import tgit.inventory.app.R;

public class SplitActivity extends ActionBarActivity implements View.OnClickListener {
    public static final String TAG = SplitActivity.class.getSimpleName();
    private EditText edtItemCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        edtItemCode = (EditText) findViewById(R.id.edtItemCode);
        Button searchBtn = (Button) findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(this);

        Button clearBtn = (Button) findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_split, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearch:
                onSplitBtnClicked();
                break;
            case R.id.btnClear:
                onClearBtnClicked();
                break;
        }
    }

    private void onClearBtnClicked() {
        edtItemCode.setText("");
        edtItemCode.requestFocus();
    }

    public void onSplitBtnClicked(){
        String itemCode = edtItemCode.getText().toString().trim();
        if (itemCode.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name)
                    .setMessage(R.string.split_search_alert_msg)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        Intent i = new Intent(this, SplitProductsActivity.class);
        i.putExtra(Constants.ITEM_CODE, itemCode);
        startActivity(i);
    }
}
