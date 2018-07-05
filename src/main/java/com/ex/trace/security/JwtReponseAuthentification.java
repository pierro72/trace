package com.ex.trace.security;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtReponseAuthentification implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtReponseAuthentification(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
