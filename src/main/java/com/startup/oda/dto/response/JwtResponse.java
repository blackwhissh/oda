package com.startup.oda.dto.response;

public class JwtResponse {
    private String accessToken;
    private final String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String email;
    private String role;

    public JwtResponse(String accessToken, String refreshToken, Long id, String email, String role) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.role = role;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
