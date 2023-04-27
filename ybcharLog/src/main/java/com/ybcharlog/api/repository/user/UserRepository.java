package com.ybcharlog.api.repository.user;

import com.ybcharlog.api.domain.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
