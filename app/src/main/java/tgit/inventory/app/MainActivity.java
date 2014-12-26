package tgit.inventory.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import tgit.config.Config;
import tgit.inventory.ui.*;
import tgit.session.CurrentUser;
import tgit.util.UIHelper;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }

        if(CurrentUser.isLogin()){
            Log.v(TAG, CurrentUser.getUserId()+" 登录");
        }
        else{
            navigateToLoginScreen();
        }
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
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Button btnInvIn = (Button) rootView.findViewById(R.id.btnInvIn);
            Button btnInvOut = (Button) rootView.findViewById(R.id.btnInvOut);
            Button btnInvSplit = (Button) rootView.findViewById(R.id.btnInvSplit);
            btnInvIn.setOnClickListener(this);
            btnInvOut.setOnClickListener(this);
            btnInvSplit.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v) {
            int inv_type;
            Intent i;
            switch (v.getId()) {
                case R.id.btnInvIn:
                    inv_type = 0;
                    i = new Intent(getActivity(), InvInActivity.class);
                    i.putExtra(Config.INV_TYPE, inv_type);
                    startActivity(i);
                    break;
                case R.id.btnInvOut:
                    i = new Intent(getActivity(), InvOutActivity.class);
                    startActivity(i);
                    break;
                case R.id.btnInvSplit:
                    i = new Intent(getActivity(), SplitActivity.class);
                    startActivity(i);
                    break;
            }
        }
    }
}
