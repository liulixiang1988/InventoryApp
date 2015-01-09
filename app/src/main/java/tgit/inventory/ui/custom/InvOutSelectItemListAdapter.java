package tgit.inventory.ui.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.model.Product;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by liulixiang on 2015/1/9.
 * Description:
 */
public class InvOutSelectItemListAdapter extends BaseAdapter {
    public static final String TAG = InvOutSelectItemListAdapter.class.getSimpleName();
    private List<Product> mProducts = null;
    private Context mContext = null;
    private String mDeliveryNumber = null;

    public InvOutSelectItemListAdapter(Context context, List<Product> products, String deliveryNumber){
        mContext = context;
        mProducts = products;
        mDeliveryNumber = deliveryNumber;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inv_out_select_item, null);
            item = new ViewHolder();
            item.txtProductNo = (TextView) convertView.findViewById(R.id.txtProductNo);
            item.txtInLocator = (TextView) convertView.findViewById(R.id.txtInLocator);
            item.txtWeight = (TextView) convertView.findViewById(R.id.txtWeight);
            item.btnChoose = (Button) convertView.findViewById(R.id.btnChoose);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }

        final Product currentProduct = mProducts.get(position);
        item.txtProductNo.setText("编号:"+currentProduct.getProductNo());
        item.txtInLocator.setText("货位:"+currentProduct.getInLocator());
        item.txtWeight.setText(mProducts.get(position).getSuttle());
        item.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "btnChoose clicked!");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("选择确认")
                        .setMessage("确认选择当前项：\n编号："+currentProduct.getProductNo()
                        +"货位："+currentProduct.getId()
                        +"重量："+currentProduct.getSuttle())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.v(TAG, "确认选择");
                                chooseItem(position);
                            }
                        })
                .setPositiveButton(android.R.string.cancel, null);

                builder.create().show();
            }
        });
        return convertView;
    }

    private void chooseItem(int position){
        Product product = mProducts.get(position);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DeliveryNumber", mDeliveryNumber);
            jsonObject.put("ItemId", product.getId());
        } catch (JSONException e) {
            Log.e(TAG, "选择产品构建发送参数出错", e);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.title_activity_split_product)
                    .setMessage("保存出错："+e.getMessage())
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

        try {
            final ProgressDialog progressDialog = ProgressDialog.show(mContext, "保存拆分",
                    "正在保存数据，请稍后", true, true);
            Log.v(TAG, "发送请求"+Config.INV_DELIVERY_ADD_ITEM);
            RestClient.postJson(mContext, Config.INV_DELIVERY_ADD_ITEM, jsonObject, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    progressDialog.dismiss();
                    Log.v(TAG, "返回JSONObject:"+response.toString());
                    try {
                        int status = response.getInt("status");
                        String msg = response.getString("msg");
                        if(status == 0){
                            UIHelper.alert(mContext,
                                    "保存失败",
                                    msg);
                        }else{
                            UIHelper.toastMessage(mContext, msg);
                            notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        UIHelper.alert(mContext, "保存失败",
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
            UIHelper.alert(mContext, "保存失败", e.getMessage());
        }
    }

    public class ViewHolder{
        public TextView txtProductNo;
        public TextView txtInLocator;
        public TextView txtWeight;
        public Button btnChoose;
    }
}
