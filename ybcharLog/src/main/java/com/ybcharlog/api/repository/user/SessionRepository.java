package com.ybcharlog.api.repository.user;

import com.ybcharlog.api.domain.auth.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByAccessToken(String accessToken);
}
