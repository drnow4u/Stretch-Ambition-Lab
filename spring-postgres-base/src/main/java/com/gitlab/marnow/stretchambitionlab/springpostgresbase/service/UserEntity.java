package com.gitlab.marnow.stretchambitionlab.springpostgresbase.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = UserEntity.TABLE_NAME)
@Data
@NoArgsConstructor
@Accessors(fluent = true)
public class UserEntity implements Serializable {

    public static final String TABLE_NAME = "users";

    @Id
    @Column(name = "user_id")
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NaturalId
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

}
