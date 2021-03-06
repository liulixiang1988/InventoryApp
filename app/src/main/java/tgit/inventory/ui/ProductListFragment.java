package tgit.inventory.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import tgit.model.VProduct;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * VProduct List fragment containing a simple view.
 */
public class ProductListFragment extends ListFragment {
    public static final String TAG = ProductListFragment.class.getSimpleName();
    private List<VProduct> mVProducts = new ArrayList<VProduct>();
    private String mItemCode;
    public static final String KEY_PRODUCT = "KEY_PRODUCT";
    public static final String KEY_INLOCATOR = "KEY_INLOCATOR";

    public ProductListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        mItemCode = getActivity().getIntent().getStringExtra(Constants.ITEM_CODE);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        loadData(mItemCode);
    }

    @Override
    public void onPause() {
        Log.v(TAG, "OnPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "OnStop");
        super.onStop();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String itemId = this.mVProducts.get(position).getId();
        Intent i = new Intent(getActivity(), SplitProductActivity.class);
        i.putExtra(Constants.ITEM_ID, itemId);
        startActivity(i);
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
                    setListAdapter(adapter);
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
