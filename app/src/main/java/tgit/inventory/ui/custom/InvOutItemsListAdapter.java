package tgit.inventory.ui.custom;

import android.app.AlertDialog;
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
import org.json.JSONObject;
import tgit.Infrastucture.IListDataSource;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.model.DeliveryDetail;
import tgit.net.RestClient;

import java.util.List;

/**
 * Created by liulixiang on 2015/1/8.
 * Description:
 */
public class InvOutItemsListAdapter extends BaseAdapter {
    public static final String TAG = InvOutItemsListAdapter.class.getSimpleName();
    private List<DeliveryDetail> mDetails = null;
    private Context mContext = null;
    private IListDataSource mDataSource = null;

    public InvOutItemsListAdapter(Context context, List<DeliveryDetail> products){
        mContext = context;
        mDetails = products;
    }

    public void setDataSource(IListDataSource dataSource){
        mDataSource = dataSource;
    }

    @Override
    public int getCount() {
        return mDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return mDetails.get(position);
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

        item.txtProductNo.setText("编号:" + mDetails.get(position).getProductNo());
        item.txtInLocator.setText("货位:"+ mDetails.get(position).getInLocator());
        item.txtWeight.setText(mDetails.get(position).getSuttle());
        item.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Delete Button is pressed");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("确认删除")
                        .setMessage("确认删除当前项？\n")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = mDetails.get(position).getId();
                                deleteItem(id);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
            }
        });

        return convertView;
    }

    private void deleteItem(int id){
        Log.v(TAG, "delete item"+id);
        RestClient.post(Config.getInvOutRemove(id), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.v(TAG, "delete item result:"+response.toString());
                if (mDataSource != null) mDataSource.refreshDataSource();
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
