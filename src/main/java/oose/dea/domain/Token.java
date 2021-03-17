package oose.dea.domain;

import java.util.UUID;

public class Token {
    private String token;
    private String user;

    public Token(String user){
        this.user = user;
        this.token = UUID.randomUUID().toString();
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
