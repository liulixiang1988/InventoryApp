package tgit.inventory.ui;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;

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
import tgit.model.Product;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ProductListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
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
     * Product List fragment containing a simple view.
     */
    public static class ProductListFragment extends ListFragment {
        public static final String TAG = ProductListFragment.class.getSimpleName();
        private List<Product> productList = new ArrayList<Product>();
        public static final String KEY_PRODUCT = "KEY_PRODUCT";
        public static final String KEY_INLOCATOR = "KEY_INLOCATOR";

        public ProductListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_products, container, false);
            String itemCode = getActivity().getIntent().getStringExtra(Constants.ITEM_CODE);
            RestClient.get(Config.getItemsURL(itemCode), null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        int length = response.length();
                        ArrayList<HashMap<String, String>> products =
                                new ArrayList<HashMap<String, String>>();

                        Gson g = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = response.getJSONObject(i);

                            String productString = obj.toString();
                            Log.v(TAG, productString);

                            Product product = g.fromJson(productString, Product.class);
                            productList.add(product);

                            HashMap<String, String> productInfo = new HashMap<String, String>();
                            productInfo.put(KEY_PRODUCT, product.getProductName() + "-" + product.getProductNo());
                            productInfo.put(KEY_INLOCATOR, product.getInLocator());
                            products.add(productInfo);
                        }
                        Log.v(TAG, productList.toString());
                        String[] keys = { KEY_PRODUCT, KEY_INLOCATOR };
                        int[] ids = { android.R.id.text1, android.R.id.text2 };
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
            });
            return rootView;
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            String itemId = this.productList.get(position).getId();
            Intent i = new Intent(getActivity(), SplitProductActivity.class);
            i.putExtra(Constants.ITEM_ID, itemId);
            startActivity(i);
        }
    }
}
