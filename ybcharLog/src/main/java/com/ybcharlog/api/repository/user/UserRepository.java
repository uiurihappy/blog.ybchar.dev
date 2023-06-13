package com.ybcharlog.api.repository.user;

import com.ybcharlog.api.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
