package com.demo.jooq.domain.service;

import com.demo.jooq.domain.models.UserReq;
import com.demo.jooq.domain.models.UserRes;
import com.demo.jooq.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserRes> getAll() {
        return userRepository.findAllUsers();
    }

    public UserRes getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserRes createUser(UserReq user) {
        return userRepository.save(user);
    }

    public Optional<UserRes> updateUser(Long id, UserReq user) {
        if (!userRepository.existsById(id)) {
            return Optional.empty();
        }
        user.setId(id);
        return Optional.of(userRepository.save(user));
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

}
