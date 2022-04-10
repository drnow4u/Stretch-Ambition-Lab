package com.gitlab.marnow.stretchambitionlab.springpostgresbase;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ExtendWith(CleanUpPostgresDataBase.class)
@Retention(RUNTIME)
public @interface CleanupDataBase {
}
