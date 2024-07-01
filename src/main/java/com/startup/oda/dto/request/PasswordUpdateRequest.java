package com.startup.oda.dto.request;

public class PasswordUpdateRequest {
    private String newPassword;
    private String oldPassword;

    public PasswordUpdateRequest(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
