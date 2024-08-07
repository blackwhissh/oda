package com.startup.oda.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "image_data")
public class ImageData {
    @Id
    @SequenceGenerator(sequenceName = "image_id_seq", name = "image_id_seq" ,allocationSize = 1)
    @GeneratedValue(generator = "image_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "image", length = 1000)
    private byte[] imageData;
    @OneToOne
    private User user;

    public ImageData(String name, String type, byte[] imageData, User user) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.user = user;
    }

    public ImageData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
