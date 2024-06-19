package com.startup.oda.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @SequenceGenerator(name = "review_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "review_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id")
    private Long reviewId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Column(name = "text")
    private String text;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "creation_date")
    private final LocalDate creationDate = LocalDate.now();

    public Review(Long reviewId, User author, String text, Integer rating) {
        this.reviewId = reviewId;
        this.user = author;
        this.text = text;
        this.rating = rating;
    }

    public Review() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public User getAuthor() {
        return user;
    }

    public void setAuthor(User author) {
        this.user = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", author=" + user +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }
}
