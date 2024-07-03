package com.startup.oda.repository;

import com.startup.oda.entity.ImageData;
import com.startup.oda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByUser(User user);

}
