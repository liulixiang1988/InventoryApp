package tgit.model;

import java.util.Date;

/**
 * Created by liulixiang on 2014/12/25.
 */
public class User {
    private String user_id;
    private String user_name;
    private String email;
    private Date last_login;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    @Override
    public String toString() {
        return user_id+":"+user_name;
    }
}
