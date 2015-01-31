package tgit.inventory.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Button;
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
            Button btnInvIn = (Button) v.findViewById(R.id.btnInvIn);
            btnInvIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int inv_type = 0;
                    Intent i = new Intent(getActivity(), InvInActivity.class);
                    i.putExtra(Config.INV_TYPE, inv_type);
                    startActivity(i);
                }
            });

            Button btnInvOut = (Button)v.findViewById(R.id.btnInvOut);
            btnInvOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), InvOutActivity.class);
                    startActivity(i);
                }
            });

            Button btnSplit = (Button) v.findViewById(R.id.btnSplit);
            btnSplit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), SplitActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
