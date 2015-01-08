package tgit.inventory.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import android.widget.Button;
import android.widget.ListView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.config.Constants;
import tgit.inventory.app.R;
import tgit.inventory.ui.custom.InvOutItemsListAdapter;
import tgit.inventory.ui.custom.PrintListAdapter;
import tgit.model.Product;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class InvOutDeliveryListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_out_delivery_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inv_out_delivery_list, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        public static final int ADD_BTN_REQUEST_CODE = 0;
        public static final String TAG = PlaceholderFragment.class.getSimpleName();

        private List<Product> mProducts = new ArrayList<Product>();
        private String mDeliveryNumber;

        private ListView listView;
        private Button btnAdd;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inv_out_delivery_list, container, false);
            init(rootView);
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

        }

        public void init(View rootView){
            listView = (ListView) rootView.findViewById(R.id.lv);
            listView.setEmptyView(rootView.findViewById(android.R.id.empty));
            mDeliveryNumber = getActivity().getIntent().getStringExtra(Constants.DELIVERY_NUMBER);
            btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(this);
        }

        private void loadData(final String deliveryNumber){
            //TODO:加载数据
            Log.v(TAG, "loadData加载数据 deliveryNumber:" + deliveryNumber);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.btnAdd:
                    btnAddClicked();
                    break;
            }
        }

        private void btnAddClicked(){
            Intent i = new Intent(getActivity(), InvOutItemSelectActivity.class);
            startActivityForResult(i, ADD_BTN_REQUEST_CODE);
        }
    }
}
