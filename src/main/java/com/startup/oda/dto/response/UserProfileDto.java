package com.startup.oda.dto.response;

public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isVerified;
    private String bio;

    public UserProfileDto(String firstName, String lastName, String email, Boolean isVerified, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isVerified = isVerified;
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
