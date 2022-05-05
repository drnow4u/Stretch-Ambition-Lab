package com.gitlab.marnow.stretchambitionlab.springpostgresbase.user;

import java.util.Objects;
import java.util.UUID;

public final class UserId {
    private final UUID id;

    public UserId(UUID id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            throw new AssertionError("Not allowed comparing with: " +  o.getClass());
        }

        UserId userId = (UserId) o;

        return Objects.equals(id, userId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static UserId of(UUID id) {
        return new UserId(id);
    }

    public static UserId of(String id) {
        return of(UUID.fromString(id));
    }

    public UUID toUUID() {
        return id;
    }
}
