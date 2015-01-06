package tgit.inventory.ui.custom;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.List;

/**
 * Created by liulixiang on 2015/1/6.
 * Description:打印标签
 */
public class PrintListAdapter extends BaseAdapter {
    public static final String TAG = PrintListAdapter.class.getSimpleName();
    private List<Product> mProducts = null;
    private Context mContext = null;

    public PrintListAdapter(List<Product> products, Context context){
        super();
        mProducts = products;
        mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.print_item, null);
            item = new ViewHolder();
            item.txtProductNo = (TextView)convertView.findViewById(R.id.txtProductNo);
            item.txtInLocator = (TextView)convertView.findViewById(R.id.txtInLocator);
            item.txtWeight = (TextView)convertView.findViewById(R.id.txtWeight);
            item.btnPrint = (Button)convertView.findViewById(R.id.btnPrint);
            convertView.setTag(item);
        } else {
            item = (ViewHolder)convertView.getTag();
        }
        item.txtProductNo.setText("产品编号:"+mProducts.get(position).getProductNo());
        item.txtInLocator.setText("货位:"+mProducts.get(position).getInLocator());
        item.txtWeight.setText(mProducts.get(position).getSuttle());
        item.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "btnPrint clicked!");
                final ProgressDialog progressDialog =  ProgressDialog.show(mContext, "标签打印", "正在将标签加入打印队列，请稍后", true, true);
                RestClient.post(Config.getPrintURL(mProducts.get(position).getId()), null, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        try {
                            int status = response.getInt("status");
                            if (status == 1){
                                UIHelper.toastMessage(mContext, "成功添加标签到打印队列");
                            }else{
                                UIHelper.alert(mContext, "失败", "未能将标签加入到打印队列");
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "jsonException", e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Log.e(TAG, "btnPrint request failure:"+throwable.getMessage(), throwable);
                    }
                });
            }
        });
        return convertView;
    }

    public class ViewHolder{
        public TextView txtProductNo;
        public TextView txtInLocator;
        public TextView txtWeight;
        public Button btnPrint;
    }
}


