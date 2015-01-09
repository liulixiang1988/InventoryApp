package tgit.inventory.ui.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import tgit.inventory.app.R;
import tgit.model.VProduct;

import java.util.List;

/**
 * Created by liulixiang on 2015/1/9.
 * Description:
 */
public class InvOutSelectItemListAdapter extends BaseAdapter {
    public static final String TAG = InvOutSelectItemListAdapter.class.getSimpleName();
    private List<VProduct> mProducts = null;
    private Context mContext = null;
    private String mDeliveryNumber = null;

    public InvOutSelectItemListAdapter(Context context, List<VProduct> products, String deliveryNumber){
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
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }

        final VProduct currentProduct = mProducts.get(position);
        item.txtProductNo.setText("编号:"+currentProduct.getProductNo());
        item.txtInLocator.setText("货位:"+currentProduct.getInLocator());
        item.txtWeight.setText(mProducts.get(position).getSuttle());
        return convertView;
    }


    public class ViewHolder{
        public TextView txtProductNo;
        public TextView txtInLocator;
        public TextView txtWeight;
    }
}
