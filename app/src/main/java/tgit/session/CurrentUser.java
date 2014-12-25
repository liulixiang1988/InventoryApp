package tgit.session;

import tgit.model.User;
import tgit.net.RestClient;

import java.util.Date;

/**
 * Created by liulixiang on 2014/12/25.
 */
public class CurrentUser {

    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLogin(){
        if(currentUser == null) return false;
        return true;
    }

    public static String getUserId(){
        return currentUser == null ? "anonymous": currentUser.getUser_id();
    }

    public static String getUserName(){
        return currentUser == null ? "anonymous": currentUser.getUser_name();
    }

    public static String getEmail(){
        return currentUser == null ? "" : currentUser.getEmail();
    }

    public static Date getLastLoginTime(){
        return currentUser == null ? null : currentUser.getLast_login();
    }

    public static void Logout(){
        currentUser = null;
        RestClient.getClient().removeHeader("Authorization");
    }
}
