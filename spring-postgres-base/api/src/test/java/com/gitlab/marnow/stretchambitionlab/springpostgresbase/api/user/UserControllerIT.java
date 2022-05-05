package com.gitlab.marnow.stretchambitionlab.springpostgresbase.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.marnow.stretchambitionlab.springpostgresbase.CleanupDataBase;
import com.gitlab.marnow.stretchambitionlab.springpostgresbase.PostgresqlContainer;
import com.gitlab.marnow.stretchambitionlab.springpostgresbase.user.UserEntity;
import com.gitlab.marnow.stretchambitionlab.springpostgresbase.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static com.gitlab.marnow.stretchambitionlab.springpostgresbase.api.user.UserController.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@CleanupDataBase
class UserControllerIT {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getInstance();

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @CsvSource({
            //     name,                   email,
            "    Marcin,     marcin@example.com",
            "      John,       john@example.com",
    })
    void shouldAddUser(String name, String email) throws Exception {
        // Given
        final var user = UserDTO.builder()
                .name(name)
                .email(email)
                .build();

        // When
        final var response = mvc.perform(post(API_USER_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // Then
        then(userRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(new UserEntity()
                        .name(name)
                        .email(email)
                );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void shouldGetUsers() throws Exception {
        // Given
        userRepository.save(new UserEntity()
                .id(UUID.fromString("8dae2633-8f24-44d1-aaa5-f90d10595730"))
                .name("Marcin")
                .email("marcin@example.com")
        );

        // When
        final var response = mvc.perform(get(API_USER_ALL)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Marcin"))
                .andExpect(jsonPath("$[0].email").value("marcin@example.com"));
    }

    @Test
    void shouldGetUser() throws Exception {
        // Given
        userRepository.save(new UserEntity()
                .id(UUID.fromString("8dae2633-8f24-44d1-aaa5-f90d10595730"))
                .name("Marcin")
                .email("marcin@example.com")
        );

        // When
        final var response = mvc.perform(get(API_USER_BY_ID, "8dae2633-8f24-44d1-aaa5-f90d10595730")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Marcin"))
                .andExpect(jsonPath("$.email").value("marcin@example.com"));
    }

    @Test
    void shouldNotGetUser() throws Exception {
        // Given
        userRepository.save(new UserEntity()
                .id(UUID.fromString("8dae2633-8f24-44d1-aaa5-f90d10595730"))
                .name("Marcin")
                .email("marcin@example.com")
        );

        // When
        final var response = mvc.perform(get(API_USER_BY_ID, "d5ac706f-1447-467c-b208-7485c0adf8b9")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("Element not exists"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        // Given
        userRepository.save(new UserEntity()
                .id(UUID.fromString("8dae2633-8f24-44d1-aaa5-f90d10595730"))
                .name("Marcin")
                .email("marcin@example.com")
        );

        // When
        final var response = mvc.perform(delete(API_USER_DELETE_BY_ID, "8dae2633-8f24-44d1-aaa5-f90d10595730")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andExpect(status().isOk());

        then(userRepository.findAll()).isEmpty();
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // Given
        userRepository.save(new UserEntity()
                .id(UUID.fromString("8dae2633-8f24-44d1-aaa5-f90d10595730"))
                .name("Marcin")
                .email("marcin@example.com")
        );

        // When
        final var user = UserDTO.builder()
                .name("John")
                .email("marcin@example.com")
                .build();

        final var response = mvc.perform(put(API_USER_DELETE_BY_ID, "8dae2633-8f24-44d1-aaa5-f90d10595730")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
        // Then
        response.andExpect(status().isOk());

        then(userRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(new UserEntity()
                        .name("John")
                        .email("marcin@example.com")
                );

    }

}
