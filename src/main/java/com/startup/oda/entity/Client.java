package com.startup.oda.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @SequenceGenerator(name = "client_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "client_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "owner_id")
    private Long clientId;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Request> requests = new ArrayList<>();

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

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", user=" + user +
                ", requests=" + requests +
                '}';
    }
}
