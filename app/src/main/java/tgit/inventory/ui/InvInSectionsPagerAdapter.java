package tgit.inventory.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import tgit.inventory.app.R;

import java.util.Locale;

/**
 * Created by liulixiang on 2015/2/6.
 * Description:
 */
public class InvInSectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public InvInSectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return InvInScanFragment.newInstance();
            case 1:
                return InvInSelectFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_in_scan).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_in_select).toUpperCase(l);
        }
        return null;
    }
}
