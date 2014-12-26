package tgit.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by liulixiang on 2014/12/24.
 */
public class UIHelper {
    /**
     * 弹出Toast消息
     *
     * @param msg
     */
    public static void toastMessage(Context cont, String msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastMessage(Context cont, int msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastMessage(Context cont, String msg, int time) {
        Toast.makeText(cont, msg, time).show();
    }

    public static void alert(Context con, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }

    public static void alert(Context con, int titleId, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(titleId)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }
}