package br.comautenticjwt.Authentic_ms.app.api;


import lombok.Data;

@Data
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
