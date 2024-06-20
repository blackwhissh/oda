package com.startup.oda.entity;

import com.startup.oda.entity.enums.RoleEnum;
import com.startup.oda.entity.enums.TransactionEnum;
import com.startup.oda.entity.enums.TypeEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();
    @Column(name = "reg_date")
    private final LocalDate registrationDate = LocalDate.now();
    @Column(name = "phone")
    private String phone;


    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                ", isVerified=" + isVerified +
                ", reviews=" + reviews +
                ", registrationDate=" + registrationDate +
                ", phone='" + phone + '\'' +
                '}';
    }
}
