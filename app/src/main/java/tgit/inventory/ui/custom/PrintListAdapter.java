package tgit.inventory.ui.custom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import tgit.inventory.app.R;
import tgit.model.Product;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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


