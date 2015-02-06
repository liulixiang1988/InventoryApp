package tgit.inventory.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import tgit.config.Config;
import tgit.inventory.app.R;
import tgit.net.RestClient;
import tgit.util.UIHelper;

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvInScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvInScanFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = InvInScanFragment.class.getSimpleName();

    private EditText edtItemCode, edtInvCode;
    private TextView txtItemCodes;

    public static InvInScanFragment newInstance() {
        InvInScanFragment fragment = new InvInScanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InvInScanFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_inv_in_scan, container, false);
        initControl(rootView);
        return rootView;
    }

    private void initControl(View view){
        Button btnAddItem = (Button) view.findViewById(R.id.btnAddItem);
        Button btnSend = (Button) view.findViewById(R.id.btnSend);
        Button btnClear = (Button) view.findViewById(R.id.btnClear);

        edtItemCode = (EditText) view.findViewById(R.id.edtItemCode);
        edtInvCode = (EditText) view.findViewById(R.id.edtInvCode);

        txtItemCodes = (TextView) view.findViewById(R.id.txtItemCodes);

        btnAddItem.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //清空
                txtItemCodes.setText("");
                edtInvCode.setText("");
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAddItem:
                btnAddItem_onClick();
                break;
            case R.id.btnSend:
                btnSend_onClick();
                break;
            default:
                break;
        }
    }

    //添加物料
    private void btnAddItem_onClick(){
        txtItemCodes.append(getItemCode()+"\n");
        edtItemCode.setText("");
        edtItemCode.requestFocus();
    }

    //发送数据
    private void btnSend_onClick(){
        Log.v(TAG, "发送按钮点击");
        if(TextUtils.isEmpty(getItemsCode()) || TextUtils.isEmpty(edtInvCode.getText())){
            UIHelper.toastMessage(getActivity(), "物料条码和货位条码不能为空");
            return;
        }
        JSONObject jsonParams = new JSONObject();
        try {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "入库", "正在保存，请稍后", true, true);
            jsonParams.put("items_code", getItemsCode());
            jsonParams.put("inventory_code", getInvCode());
            Log.v(TAG, jsonParams.toString());
            StringEntity entity = new StringEntity(jsonParams.toString());
            RestClient.postJson(getActivity(), Config.INV_IN, entity, new JsonHttpResponseHandler() {
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
                            clearText();
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
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        catch (JSONException e) {
            Log.e(TAG, "B发生错误："+e.toString());
            UIHelper.toastMessage(getActivity(), e.toString());
        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, "C发生错误："+e.toString());
            UIHelper.toastMessage(getActivity(), e.toString());
        }
        catch(Exception e){
            Log.e(TAG, "D发生错误" + e.toString());
        }
        Log.v(TAG, "发送结束");
    }

    private void clearText(){
        edtItemCode.getText().clear();
        edtItemCode.requestFocus();
        txtItemCodes.setText("");
    }

    public String getItemCode(){
        return edtItemCode.getText().toString();
    }

    public String getInvCode(){
        return edtInvCode.getText().toString();
    }

    public String getItemsCode(){
        return txtItemCodes.getText().toString().replace('\n', ';');
    }
}
