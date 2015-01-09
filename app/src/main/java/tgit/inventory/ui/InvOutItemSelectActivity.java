package tgit.inventory.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import android.widget.*;
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
import tgit.inventory.ui.custom.InvOutSelectItemListAdapter;
import tgit.model.VProduct;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.io.UnsupportedEncodingException;
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
    public static class InvOutProductSelectFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
        public static final String TAG = InvOutProductSelectFragment.class.getSimpleName();
        private List<VProduct> mVProducts = new ArrayList<VProduct>();
        public static final String KEY_PRODUCT = "KEY_PRODUCT";
        public static final String KEY_INLOCATOR = "KEY_INLOCATOR";

        private String mDeliveryNumber;
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
            mDeliveryNumber = getActivity().getIntent().getStringExtra(Constants.DELIVERY_NUMBER);
            Log.v(TAG, "DeliveryNumber:"+mDeliveryNumber);
            txtProductNo = (TextView) rootView.findViewById(R.id.txtProductNo);
            btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
            listView = (ListView) rootView.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);

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

                        Gson g = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = response.getJSONObject(i);

                            String productString = obj.toString();
                            Log.v(TAG, productString);

                            VProduct VProduct = g.fromJson(productString, VProduct.class);
                            mVProducts.add(VProduct);

                        }
                        Log.v(TAG, mVProducts.toString());
                        InvOutSelectItemListAdapter adapter =
                                new InvOutSelectItemListAdapter(
                                getActivity(),
                                mVProducts, mDeliveryNumber);
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

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            Log.v(TAG, "选择Item");
            VProduct product = mVProducts.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("选择确认")
                    .setMessage("编码："+ product.getProductNo()
                    +"\n货位："+product.getInLocator()
                    +"\n重量："+product.getSuttle())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveSelect(position);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null);
            builder.create().show();
        }

        private void saveSelect(int position){
            VProduct product = mVProducts.get(position);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DeliveryNumber", mDeliveryNumber);
                jsonObject.put("ItemId", product.getId());
            } catch (JSONException e) {
                Log.e(TAG, "选择产品构建发送参数出错", e);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_activity_split_product)
                        .setMessage("保存出错："+e.getMessage())
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }

            try {
                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "保存拆分",
                        "正在保存数据，请稍后", true, true);
                Log.v(TAG, "发送请求"+Config.INV_DELIVERY_ADD_ITEM);
                RestClient.postJson(getActivity(), Config.INV_DELIVERY_ADD_ITEM, jsonObject, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        Log.v(TAG, "返回JSONObject:"+response.toString());
                        try {
                            int status = response.getInt("status");
                            String msg = response.getString("msg");
                            if(status == 0){
                                UIHelper.alert(getActivity(),
                                        "保存失败",
                                        msg);
                            }else{
                                UIHelper.toastMessage(getActivity(), msg);
                            }
                            btnSearchOnClick();
                        } catch (JSONException e) {
                            UIHelper.alert(getActivity(), "保存失败",
                                    "返回数据有误:"+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                    }
                });
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "保存挑库行失败", e);
                UIHelper.alert(getActivity(), "保存失败", e.getMessage());
            }
        }
    }
}
