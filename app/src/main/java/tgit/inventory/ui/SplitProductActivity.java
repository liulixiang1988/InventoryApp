package tgit.inventory.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.config.Constants;
import tgit.inventory.app.R;
import tgit.model.Product;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.io.UnsupportedEncodingException;

public class SplitProductActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_product);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_split_product, menu);
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
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, TextWatcher {
        public static final String TAG = PlaceholderFragment.class.getSimpleName();
        private Product mProduct;
        private String mLeftWeight;
        private TextView txtProductName, txtInLocator, txtProductNo, txtInLocatorId,
                txtModel,txtSpecification, txtTemp, txtBatchNumber, txtProductDate, txtSuttle,
                txtLeftWeight;
        private EditText edtSplitWeight;
        private Button btnOk;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_split_product, container, false);

            initializeControl(rootView);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.v(TAG, "onResume");
            String itemId = getActivity().getIntent().getStringExtra(Constants.ITEM_ID);
            Log.v(TAG, "item id:"+itemId);
            getProduct(itemId);
        }

        private void initializeControl(View v){
            txtProductName = (TextView) v.findViewById(R.id.txtProductName);
            txtInLocator = (TextView) v.findViewById(R.id.txtInLocator);
            txtProductNo = (TextView) v.findViewById(R.id.txtProductNo);
            txtInLocatorId = (TextView) v.findViewById(R.id.txtInLocatorId);
            txtModel = (TextView) v.findViewById(R.id.txtModel);
            txtSpecification = (TextView) v.findViewById(R.id.txtSpecification);
            txtTemp = (TextView) v.findViewById(R.id.txtTemp);
            txtBatchNumber = (TextView) v.findViewById(R.id.txtBatchNumber);
            txtProductDate = (TextView) v.findViewById(R.id.txtProductDate);
            txtSuttle = (TextView) v.findViewById(R.id.txtSuttle);
            txtLeftWeight = (TextView) v.findViewById(R.id.txtLeftWeight);

            edtSplitWeight = (EditText) v.findViewById(R.id.edtSplitWeight);
            edtSplitWeight.addTextChangedListener(this);
            edtSplitWeight.clearFocus();
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSplitWeight.getWindowToken(), 0);


            btnOk = (Button) v.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(this);
        }

        private void getProduct(String itemId){
            String url = Config.getItemURL(itemId);
            Log.v(TAG, "获取产品信息："+url);
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "数据加载",
                    "正在加载数据，请稍后", true);
            RestClient.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    progressDialog.dismiss();

                    //map the result to mProduct
                    Gson g = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                            .create();
                    String responseString = response.toString();
                    Log.v(TAG, "返回值："+responseString);

                    mProduct = g.fromJson(responseString, Product.class);
                    setProductView();
                }
            });
        }

        private void setProductView(){
            if (mProduct == null) return;

            txtProductName.append(mProduct.getProductName());
            txtInLocator.append(mProduct.getInLocator());
            txtProductNo.append(mProduct.getProductNo());
            txtInLocatorId.append(mProduct.getInLocatorId());
            txtModel.append(mProduct.getModel());
            txtSpecification.append(mProduct.getSpecification());
            txtTemp.append(mProduct.getTemp());
            txtBatchNumber.append(mProduct.getBatchNumber());
            txtProductDate.append(mProduct.getProductDate());
            txtSuttle.append(mProduct.getSuttle());
        }

        @Override
        public void onClick(View v) {
            String splitWeightStr = edtSplitWeight.getText().toString().trim();
            if (mProduct == null || splitWeightStr.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_activity_split_product)
                        .setMessage("当前没有对应的记录，或者没有输入拆包重量")
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }

            double splitWeight = Double.parseDouble(splitWeightStr);
            double originWeight = Double.parseDouble(mProduct.getSuttle());
            if (originWeight < splitWeight){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_activity_split_product)
                        .setMessage("拆包重量不能大于原始重量")
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }

            keepSplit(mProduct.getId(), mLeftWeight, splitWeightStr);
        }

        private void keepSplit(String productId, String leftWeight, String splitWeight){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", productId);
                jsonObject.put("WeightLeft", leftWeight);
                jsonObject.put("WeightDelivery", splitWeight);
            } catch (JSONException e) {
                Log.e(TAG, "拆分出错", e);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_activity_split_product)
                        .setMessage("拆分出错："+e.getMessage())
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }

            try {
                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "保存拆分",
                        "正在保存数据，请稍后", true);
                RestClient.postJson(getActivity(), Config.PRODUCT_SPLIT_URL, jsonObject, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.v(TAG, "返回JSONObject:"+response.toString());
                        progressDialog.dismiss();
                        try {
                            int status = response.getInt("status");
                            String msg = response.getString("msg");
                            if(status == 0){
                                UIHelper.alert(getActivity(),
                                        R.string.title_activity_split_product,
                                        msg);
                            }else{
                                UIHelper.toastMessage(getActivity(), msg);
                                getActivity().finish();
                            }
                        } catch (JSONException e) {
                            UIHelper.alert(getActivity(), R.string.title_activity_split_product,
                                    "返回数据有误:"+e.getMessage());
                        }
                    }
                });
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "拆分发送出错", e);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_activity_split_product)
                        .setMessage("拆分发送出错："+e.getMessage())
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String splitWeightStr = edtSplitWeight.getText().toString().trim();
            if (splitWeightStr.isEmpty()) return;
            double splitWeight = Double.parseDouble(splitWeightStr);
            double originWeight = Double.parseDouble(mProduct.getSuttle());
            double leftWeight = originWeight - splitWeight;
            mLeftWeight = String.format("%1.3f", leftWeight);
            txtLeftWeight.setText("剩余量："+mLeftWeight);

        }
    }
}
