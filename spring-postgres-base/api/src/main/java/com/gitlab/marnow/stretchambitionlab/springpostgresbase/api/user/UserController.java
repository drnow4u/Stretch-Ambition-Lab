package com.gitlab.marnow.stretchambitionlab.springpostgresbase.api.user;

import com.gitlab.marnow.stretchambitionlab.springpostgresbase.service.user.User;
import com.gitlab.marnow.stretchambitionlab.springpostgresbase.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String API_USER_CREATE = "/api/user";
    public static final String API_USER_ALL = "/api/user";
    public static final String API_USER_BY_ID = "/api/user/{userId}";
    public static final String API_USER_DELETE_BY_ID = "/api/user/{userId}";
    public static final String API_USER_UPDATE_BY_ID = "/api/user/{userId}";

    private final UserService userService;

    @GetMapping(API_USER_ALL)
    public List<@Valid UserDTO> getUsers() {
        return userService.retrieveUsers().stream()
                .map(UserController::toUserDTO)
                .toList();
    }

    @GetMapping(API_USER_BY_ID)
    public UserDTO getUserById(@PathVariable(name = "userId") UUID userId) {
        return userService.getUser(userId)
                .map(UserController::toUserDTO)
                .orElseThrow();
    }

    @PostMapping(API_USER_CREATE)
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
        return toUserDTO(userService.createUser(user));
    }

    private static UserDTO toUserDTO(User createdUser) {
        return UserDTO.builder()
                .id(createdUser.getId().toUUID())
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .build();
    }

    @DeleteMapping(API_USER_DELETE_BY_ID)
    public void deleteUser(@PathVariable(name = "userId") UUID usetId) {
        userService.deleteUser(usetId);
    }

    @PutMapping(API_USER_UPDATE_BY_ID)
    public UserDTO updateUser(@PathVariable(name = "userId") UUID userId, @RequestBody @Valid UserDTO user) {
        return userService.updateUser(userId,
                        User.builder()
                                .name(user.getName())
                                .email(user.getEmail())
                                .build())
                .map(UserController::toUserDTO)
                .orElseThrow();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleException(NoSuchElementException e) {
        return new ErrorDTO("Element not exists");
    }

    public record ErrorDTO(String msg) {
    }

}
