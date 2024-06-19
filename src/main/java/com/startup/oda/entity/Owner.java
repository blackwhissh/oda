package com.startup.oda.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @SequenceGenerator(name = "owner_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "owner_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "owner_id")
    private Long clientId;
    @OneToOne
    private User user;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Property> properties = new ArrayList<>();

    public Owner() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "clientId=" + clientId +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", properties=" + properties +
                '}';
    }
}
