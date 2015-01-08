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
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.model.Product;
import tgit.net.RestClient;

import java.util.List;

/**
 * Created by liulixiang on 2015/1/8.
 * Description:
 */
public class InvOutItemsListAdapter extends BaseAdapter {
    public static final String TAG = InvOutItemsListAdapter.class.getSimpleName();
    private List<Product> mProducts = null;
    private Context mContext = null;

    public InvOutItemsListAdapter(Context context, List<Product> products){
        mContext = context;
        mProducts = products;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inv_out_item, null);
            item = new ViewHolder();
            item.txtProductNo = (TextView)convertView.findViewById(R.id.txtProductNo);
            item.txtInLocator = (TextView)convertView.findViewById(R.id.txtInLocator);
            item.txtWeight = (TextView)convertView.findViewById(R.id.txtWeight);
            item.btnDelete = (Button)convertView.findViewById(R.id.btnDelete);
            convertView.setTag(item);
        } else {
            item = (ViewHolder)convertView.getTag();
        }

        item.txtProductNo.setText("编号:"+mProducts.get(position).getProductNo());
        item.txtInLocator.setText("货位:"+mProducts.get(position).getInLocator());
        item.txtWeight.setText(mProducts.get(position).getSuttle());
        item.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Delete Button is pressed");
                String id = mProducts.get(position).getId();
                deleteItem(id);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void deleteItem(String id){
        Log.v(TAG, "delete item"+id);
        RestClient.post(Config.getInvOutRemove(id), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.v(TAG, "delete item result:"+response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "error in delete item:", throwable);
            }
        });
    }

    public class ViewHolder{
        public TextView txtProductNo;
        public TextView txtInLocator;
        public TextView txtWeight;
        public Button btnDelete;
    }
}
