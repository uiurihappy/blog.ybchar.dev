package com.ybcharlog.api.service.user;

import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.UnauthorizedRequest;
import com.ybcharlog.api.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonUserService {

    private final @Lazy
    PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User add(User entity) {
        return userRepository.save(entity);
    }

    public boolean existsEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not exists user. email: " + email));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not exists user. id: " + id));
    }


    @Transactional
    public User getUser(String email, String password) {
        User user = this.getUserByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedRequest(
                    String.format("Not Authorized.(email: %s)", email));
        }
        log.info(user.getEmail(), user.getRoles(), user.getPassword());
        return user;
    }
}
