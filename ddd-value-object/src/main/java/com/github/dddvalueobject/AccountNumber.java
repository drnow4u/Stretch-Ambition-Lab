package com.github.dddvalueobject;

import java.util.Objects;
import java.util.StringJoiner;

public record AccountNumber(String value) {

    public AccountNumber {
        Objects.requireNonNull(value);
        if (value.length() != 10)
            throw new IllegalArgumentException("The size of AccountNumber has to been 10");
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClientNumber.class.getSimpleName() + "[", "]")
                .add("number='******" + value.substring(6, 9) + "'")
                .toString();
    }
}
