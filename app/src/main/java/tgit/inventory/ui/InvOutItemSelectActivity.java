package tgit.inventory.ui;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.model.VProduct;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvOutItemSelectActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_out_item_select);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new InvOutProductSelectFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inv_out_item_select, menu);
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
    public static class InvOutProductSelectFragment extends Fragment implements View.OnClickListener {
        public static final String TAG = InvOutProductSelectFragment.class.getSimpleName();
        private List<VProduct> mVProducts = new ArrayList<VProduct>();
        public static final String KEY_PRODUCT = "KEY_PRODUCT";
        public static final String KEY_INLOCATOR = "KEY_INLOCATOR";

        private TextView txtProductNo;
        private Button btnSearch;
        private ListView listView;

        public InvOutProductSelectFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inv_out_item_select, container, false);

            init(rootView);

            return rootView;
        }

        public void init(View rootView){
            txtProductNo = (TextView) rootView.findViewById(R.id.txtProductNo);
            btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
            listView = (ListView) rootView.findViewById(R.id.listView);

            listView.setEmptyView(rootView.findViewById(android.R.id.empty));
            btnSearch.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.btnSearch:
                    btnSearchOnClick();
                    break;
            }
        }

        private void btnSearchOnClick(){
            String itemCode = txtProductNo.getText().toString().trim();
            if (itemCode.isEmpty()){
                UIHelper.alert(getActivity(), "产品编号不能为空", "请输入产品编号");
                return;
            }
            loadData(itemCode);
        }

        private void loadData(String itemCode){
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "数据加载",
                    "正在加载数据，请稍后", true, true);
            mVProducts.clear();
            RestClient.get(Config.getItemsURL(itemCode), null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);

                    progressDialog.dismiss();

                    try {
                        int length = response.length();
                        ArrayList<HashMap<String, String>> products =
                                new ArrayList<HashMap<String, String>>();

                        Gson g = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = response.getJSONObject(i);

                            String productString = obj.toString();
                            Log.v(TAG, productString);

                            VProduct VProduct = g.fromJson(productString, VProduct.class);
                            mVProducts.add(VProduct);

                            HashMap<String, String> productInfo = new HashMap<String, String>();
                            productInfo.put(KEY_PRODUCT, VProduct.getProductName() + "-" + VProduct.getProductNo());
                            productInfo.put(KEY_INLOCATOR, VProduct.getInLocator());
                            products.add(productInfo);
                        }
                        Log.v(TAG, mVProducts.toString());
                        String[] keys = {KEY_INLOCATOR, KEY_PRODUCT};
                        int[] ids = {android.R.id.text1, android.R.id.text2};
                        SimpleAdapter adapter = new SimpleAdapter(
                                getActivity(),
                                products,
                                android.R.layout.simple_list_item_2,
                                keys, ids);
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        Log.e(TAG, "解析物料列表：" + e.toString(), e);
                        UIHelper.toastMessage(getActivity(), e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();

                    UIHelper.alert(getActivity(), "查找失败", throwable.getMessage());
                }
            });
        }
    }
}
