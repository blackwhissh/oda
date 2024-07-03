package com.startup.oda.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "file_data")
public class FileData {
    @Id
    @SequenceGenerator(sequenceName = "file_id_seq", name = "file_id_seq" ,allocationSize = 1)
    @GeneratedValue(generator = "file_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String type;
    private String path;

    public FileData() {
    }

    public FileData(Long id, String name, String type, String path) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
