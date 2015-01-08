package tgit.inventory.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import tgit.config.Config;
import tgit.inventory.ui.*;
import tgit.session.CurrentUser;
import tgit.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Config.initializeConfig(this);

        if (Config.getBaseURL(this).isEmpty()) {
            UIHelper.toastMessage(this, "IP地址没有配置");
            Intent i = new Intent(this, IPConfigActivity.class);
            startActivity(i);
            return;
        }
        if(CurrentUser.isLogin()){
            Log.v(TAG, CurrentUser.getUserId()+" 登录");
        }
        else{
            navigateToLoginScreen();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void navigateToLoginScreen() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if(id == R.id.action_logout) {
            CurrentUser.Logout();
            navigateToLoginScreen();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            initialControl(rootView);
            return rootView;
        }

        private void initialControl(View v){
            GridView gridview = (GridView) v.findViewById(R.id.GridView);
            ArrayList<HashMap<String, Object>> menuList = new ArrayList<HashMap<String, Object>>();
            for(int i = 0;i < 3;i++)
            {
                HashMap<String, Object> map = new HashMap<String, Object>();
                switch (i){
                    case 0:
                        map.put("ItemImage", R.drawable.inv_in);
                        map.put("ItemText", "入库");
                        break;
                    case 1:
                        map.put("ItemImage", R.drawable.inv_out);
                        map.put("ItemText", "出库");
                        break;
                    case 2:
                        map.put("ItemImage", R.drawable.split);
                        map.put("ItemText", "拆分");
                        break;
                }
                menuList.add(map);
            }
            SimpleAdapter saItem = new SimpleAdapter(getActivity(),
                    menuList, //数据源
                    R.layout.dashboard_button_layout, //xml实现
                    new String[]{"ItemImage","ItemText"}, //对应map的Key
                    new int[]{R.id.ItemImage,R.id.ItemText});  //对应R的Id

            //添加Item到网格中
            gridview.setAdapter(saItem);
            //添加点击事件
            gridview.setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        public void onItemClick(AdapterView<?> parent, View arg1, int position,long id)
                        {
                            int inv_type;
                            Intent i;
                            switch (position) {
                                case 0:
                                    inv_type = 0;
                                    i = new Intent(getActivity(), InvInActivity.class);
                                    i.putExtra(Config.INV_TYPE, inv_type);
                                    startActivity(i);
                                    break;
                                case 1:
                                    i = new Intent(getActivity(), InvOutActivity.class);
                                    startActivity(i);
                                    break;
                                case 2:
                                    i = new Intent(getActivity(), SplitActivity.class);
                                    startActivity(i);
                                    break;
                            }
                        }
                    }
            );
        }
    }
}
