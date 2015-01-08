package tgit.inventory.ui;

/**
 * Created by liulixiang on 2015/1/5.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import tgit.inventory.app.R;

import java.util.Locale;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SplitProductsSectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SplitProductsSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a InvOutProductSelectFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return new ProductListFragment();
            case 1:
                return new SplittedProductListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_unsplitted).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_splitted).toUpperCase(l);
        }
        return null;
    }
}
