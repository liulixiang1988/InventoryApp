package tgit.inventory.ui;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.Infrastucture.IListDataSource;
import tgit.config.Config;
import tgit.config.Constants;
import tgit.inventory.app.R;
import tgit.inventory.ui.custom.InvOutItemsListAdapter;
import tgit.inventory.ui.custom.InvOutSelectItemListAdapter;
import tgit.inventory.ui.custom.PrintListAdapter;
import tgit.model.DeliveryDetail;
import tgit.model.Product;
import tgit.model.VProduct;
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
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, IListDataSource {

        public static final int ADD_BTN_REQUEST_CODE = 0;
        public static final String TAG = PlaceholderFragment.class.getSimpleName();

        private List<DeliveryDetail> mDetails = new ArrayList<DeliveryDetail>();

        private ListView listView;
        private Button btnAdd;
        private TextView txtTotalWeight;
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
            Log.v(TAG, "加载数据："+Config.CurrentDeliveryNumber);
            loadData(Config.CurrentDeliveryNumber);
        }

        public void init(View rootView){
            listView = (ListView) rootView.findViewById(R.id.lv);
            listView.setEmptyView(rootView.findViewById(android.R.id.empty));
            txtTotalWeight = (TextView) rootView.findViewById(R.id.txtTotalWeight);
            btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(this);
        }

        private void loadData(final String deliveryNumber){
            Log.v(TAG, "loadData加载数据 deliveryNumber:" + deliveryNumber);
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "数据加载",
                    "正在加载数据，请稍后", true, true);
            mDetails.clear();
            RestClient.get(Config.getInvOutDeliveryDetails(deliveryNumber), null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    progressDialog.dismiss();
                    try {
                        int length = response.length();

                        Gson g = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = response.getJSONObject(i);

                            String productString = obj.toString();
                            Log.v(TAG, productString);

                            DeliveryDetail detail = g.fromJson(productString, DeliveryDetail.class);
                            mDetails.add(detail);
                        }
                        Log.v(TAG, mDetails.toString());
                        InvOutItemsListAdapter adapter =
                                new InvOutItemsListAdapter(
                                        getActivity(),
                                        mDetails);
                        adapter.setDataSource(PlaceholderFragment.this);
                        listView.setAdapter(adapter);

                        sumWeight();
                    } catch (JSONException e) {
                        Log.e(TAG, "解析物料列表：" + e.toString(), e);
                        UIHelper.toastMessage(getActivity(), e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    progressDialog.dismiss();
                    Log.e(TAG, "加载数据失败", throwable);
                    UIHelper.alert(getActivity(), "加载数据失败", "错误信息："+throwable.getMessage());
                }
            });

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
            i.putExtra(Constants.DELIVERY_NUMBER, Config.CurrentDeliveryNumber);
            startActivityForResult(i, ADD_BTN_REQUEST_CODE);
        }

        @Override
        public void refreshDataSource() {
            loadData(Config.CurrentDeliveryNumber);
        }

        private void sumWeight(){
            Log.v(TAG, "开始累加");
            double weight = 0;
            for(DeliveryDetail detail : mDetails){
                try{
                    weight += Double.parseDouble(detail.getSuttle());
                } catch(NumberFormatException e){
                    Log.e(TAG, "累加出错", e);
                }
            }
            String totalWeight = String.format("%1.3f", weight);
            txtTotalWeight.setText(totalWeight);
        }
    }
}
