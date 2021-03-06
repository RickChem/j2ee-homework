package model;

import java.io.Serializable;

/**
 * Created by raychen on 2016/12/29.
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String name;
    private int permission;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getPermission() {
        return permission;
    }
}
