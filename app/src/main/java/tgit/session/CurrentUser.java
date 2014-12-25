package tgit.session;

import tgit.model.User;

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
}
