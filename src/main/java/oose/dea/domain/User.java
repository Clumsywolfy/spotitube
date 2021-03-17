package oose.dea.domain;

import java.util.UUID;

public class User {
    private String username;
    private String password;
    private String token;
    private String user;

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}