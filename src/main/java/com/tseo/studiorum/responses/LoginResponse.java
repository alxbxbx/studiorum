package com.tseo.studiorum.responses;

public class LoginResponse {

    private String token;

    public LoginResponse(final String token) {
        this.setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
