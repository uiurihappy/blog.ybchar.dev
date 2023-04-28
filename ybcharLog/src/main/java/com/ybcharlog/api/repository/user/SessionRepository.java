package com.ybcharlog.api.repository.user;

import com.ybcharlog.api.domain.auth.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
