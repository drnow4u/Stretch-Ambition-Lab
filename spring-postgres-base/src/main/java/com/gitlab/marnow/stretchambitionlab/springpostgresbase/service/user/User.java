package com.gitlab.marnow.stretchambitionlab.springpostgresbase.service.user;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class User {
    UserId id;

    @NotBlank
    String name;

    @Email
    @NotNull
    String email;

    public static class UserBuilder {
        public UserBuilder id(UserId id) {
            this.id = id;
            return this;
        }

        public UserBuilder id(UUID id) {
            this.id = UserId.of(id);
            return this;
        }
    }

}
