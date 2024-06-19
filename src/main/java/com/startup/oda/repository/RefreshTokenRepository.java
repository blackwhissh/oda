package com.startup.oda.repository;

import com.startup.oda.entity.RefreshToken;
import com.startup.oda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);

    int deleteByUser(User user);
}
