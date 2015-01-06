package tgit.inventory.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tgit.inventory.ui.custom.PrintListAdapter;
import tgit.model.Product;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 已拆分
 */
public class SplittedProductListFragment extends ListFragment {

    public static final String TAG = SplittedProductListFragment.class.getSimpleName();

    private List<Product> mProducts = new ArrayList<Product>();
    private String mItemCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splitted_product_list, container, false);
        mItemCode = getActivity().getIntent().getStringExtra(Constants.ITEM_CODE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        loadData(mItemCode);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        String itemId = this.mProducts.get(position).getId();
//        Intent i = new Intent(getActivity(), SplitProductActivity.class);
//        i.putExtra(Constants.ITEM_ID, itemId);
//        startActivity(i);
    }

    private void loadData(final String itemCode){
        Log.v(TAG, "loadData加载数据 itemCode:"+itemCode);
        mProducts.clear();
        RestClient.get(Config.getSplitURL(itemCode), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                Log.v(TAG, "loadData完成加载 itemCode:"+itemCode);
                try {
                    int length = response.length();
                    Gson g = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                    for (int i = 0; i < length; i++) {
                        JSONObject obj = response.getJSONObject(i);

                        String productString = obj.toString();
                        Log.v(TAG, productString);

                        Product product = g.fromJson(productString, Product.class);
                        mProducts.add(product);

                    }
                    Log.v(TAG, mProducts.toString());
                    PrintListAdapter listAdapter = new PrintListAdapter(mProducts, getActivity());
                    setListAdapter(listAdapter);
                } catch (JSONException e) {
                    Log.e(TAG, "解析物料列表：" + e.toString(), e);
                    UIHelper.toastMessage(getActivity(), e.toString());
                }
            }
        });
    }
}
