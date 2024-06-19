package com.startup.oda.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agent")
public class Agent {
    @Id
    @SequenceGenerator(name = "agent_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "agent_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "agent_id")
    private Long agentId;
    @OneToOne
    private User user;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy = "agent",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Request> requests = new ArrayList<>();
    @OneToMany(mappedBy = "agent",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Property> properties = new ArrayList<>();

    public Agent() {
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentId=" + agentId +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", requests=" + requests +
                ", properties=" + properties +
                '}';
    }
}
