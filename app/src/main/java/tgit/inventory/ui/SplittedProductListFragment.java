package tgit.inventory.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tgit.inventory.app.R;

/**
 * 已拆分
 */
public class SplittedProductListFragment extends ListFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splitted_product_list, container, false);
    }
}
