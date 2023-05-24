package com.github.dddvalueobject;

import java.util.Objects;
import java.util.StringJoiner;

public record ClientNumber(String value) {

    public ClientNumber {
        Objects.requireNonNull(value);
        if (value.length() != 10)
            throw new IllegalArgumentException("The size of ClientNumber has to been 10");
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClientNumber.class.getSimpleName() + "[", "]")
                .add("number='******" + value.substring(6, 9) + "'")
                .toString();
    }

}
