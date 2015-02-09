package tgit.inventory.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.model.Datalineinfo;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InvInSelectFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = InvInSelectFragment.class.getSimpleName();

    private Button btnFresh, btnSave;
    private EditText edtInvCode;
    private ListView listView;

    private List<Datalineinfo> mItems = new ArrayList<Datalineinfo>();

    public static InvInSelectFragment newInstance() {
        InvInSelectFragment fragment = new InvInSelectFragment();

        return fragment;
    }

    public InvInSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inv_in_select, container, false);
        initControl(rootView);
        return rootView;
    }

    private void initControl(View view){
        btnFresh = (Button) view.findViewById(R.id.btnFresh);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        edtInvCode = (EditText) view.findViewById(R.id.edtInvCode);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        btnFresh.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFresh:
                btnFreshOnClick();
                break;
            case R.id.btnSave:
                btnSaveOnClick();
                break;
        }
    }

    private void btnFreshOnClick(){
        loadData();
    }

    private void btnSaveOnClick(){
        saveData();
    }

    private void loadData(){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "数据添加",
                "正在加载数据，请稍后", true, true);
        mItems.clear();
        RestClient.get(Config.getHtbUnfinished(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                progressDialog.dismiss();
                try {
                    int length = response.length();
                    Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                    String[] items = new String[length];
                    for(int i = 0; i<length; i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String datalineString = jsonObject.toString();
                        Log.v(TAG, "dataline:"+datalineString);

                        Datalineinfo dl = g.fromJson(datalineString, Datalineinfo.class);
                        items[i] = dl.getAttribute13()+"-"+dl.getAttribute15();
                        mItems.add(dl);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_checked,
                            items);
                    listView.setAdapter(adapter);
                }
                catch(JSONException e){
                    Log.e(TAG, "解析物料列表：" + e.toString(), e);
                    UIHelper.toastMessage(getActivity(), e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                UIHelper.alert(getActivity(), "查找失败", throwable.getMessage());
            }
        });
    }

    private void saveData(){
        Log.v(TAG, "发送按钮点击");
        if(TextUtils.isEmpty(edtInvCode.getText())){
            UIHelper.alert(getActivity(), "信息不全", "货位条码不能为空");
            return;
        }

        String str="";
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for(int i = 0; i < checked.size(); i++){
            int key = checked.keyAt(i);
            if(checked.get(key)){
                str += listView.getAdapter().getItem(i)+";";
            }
        }
        Log.v(TAG,"选择项："+str);
        try{
            JSONObject jsonParams = new JSONObject();
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "入库", "正在保存，请稍后", true, true);
            jsonParams.put("items_code", str);
            jsonParams.put("inventory_code", edtInvCode.getText().toString().trim());
            Log.v(TAG, jsonParams.toString());
            StringEntity entity = new StringEntity(jsonParams.toString());
            RestClient.postJson(getActivity(), Config.INV_IN, entity, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String msg = response.getString("msg");
                        Log.v(TAG, "返回的消息：" + msg);
                        UIHelper.toastMessage(getActivity(), msg);
                        int resultCode = response.getInt("status");
                        if (resultCode == 1) {
                            loadData();
                            edtInvCode.setText("");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "A发生错误：" + e.toString());
                        UIHelper.toastMessage(getActivity(), e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Log.e(TAG, "发生错误3 Statuscode:" + statusCode + " ");
                    UIHelper.toastMessage(getActivity(), "发生错误3 状态：" + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      String responseString, Throwable throwable) {
                    progressDialog.dismiss();
                    Log.e(TAG, "发生错误4+status code:" + statusCode);
                    for (Header header : headers) {
                        Log.e(TAG, header.getName() + ":" + header.getValue());
                    }
                    Log.e(TAG, responseString);
                }
            });
        }
        catch(JSONException e){
            Log.e(TAG, "B发生错误："+e.toString());
            UIHelper.toastMessage(getActivity(), e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "C发生错误："+e.toString());
            UIHelper.toastMessage(getActivity(), e.toString());
        }
    }
}
