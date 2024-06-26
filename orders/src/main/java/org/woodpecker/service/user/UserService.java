package org.woodpecker.service.user;

import org.woodpecker.repository.model.user.User;
import org.woodpecker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Cacheable("userByMail")
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new UsernameNotFoundException("User '" + email + "' was not found"));
    }


}
