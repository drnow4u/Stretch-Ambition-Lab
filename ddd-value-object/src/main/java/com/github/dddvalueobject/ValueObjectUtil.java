package com.github.dddvalueobject;

import java.util.Collection;
import java.util.Objects;

public final class ValueObjectUtil {

    private ValueObjectUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be used with Value Objects.
     */
    public static <T> ObjectsComparator<T> is(T l) {
        return new ObjectsComparator<>(l);
    }

    public static class ObjectsComparator<T> {

        private final Object l;

        public ObjectsComparator(T l) {

            this.l = l;
        }

        public boolean eq(T r) {
            if (l != null && r != null && l.getClass() != r.getClass()) {
                throw new AssertionError("Not allowed comparing different class. " +
                        "Should not happen. Unless new Java version chang it.");
            }

            return Objects.equals(l, r);
        }

        public boolean in(Collection<? extends T> r) {
            if (l == null || r == null) {
                throw new AssertionError("Not allowed comparing null value. ");
            }

            return r.contains(l);
        }

    }
}
