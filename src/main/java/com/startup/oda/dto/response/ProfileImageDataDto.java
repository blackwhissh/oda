package com.startup.oda.dto.response;

public class ProfileImageDataDto {
    private String type;
    private byte[] imageData;

    public ProfileImageDataDto(String type, byte[] imageData) {
        this.type = type;
        this.imageData = imageData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
