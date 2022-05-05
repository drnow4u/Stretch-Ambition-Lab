package com.gitlab.marnow.stretchambitionlab.springpostgresbase.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> retrieveUsers() {
        return userRepository.findAll().stream()
                .map(UserService::toUser)
                .toList();
    }

    public Optional<User> getUser(UUID userId) {
        return userRepository.findById(userId)
                .map(UserService::toUser);
    }

    public @Valid User createUser(@Valid User user) {
        return toUser(userRepository.save(new UserEntity()
                .id(UUID.randomUUID())
                .name(user.getName())
                .email(user.getEmail())
        ));
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> updateUser(UUID userId, User user) {
        return userRepository.findById(userId)
                .map(u -> u.email(user.getEmail())
                        .name(user.getName()))
                .map(userRepository::save)
                .map(UserService::toUser);
    }

    private static User toUser(UserEntity user) {
        return User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .build();
    }
}
