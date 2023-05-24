package com.github.dddvalueobject.sql;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.dddvalueobject.DemoId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class DemoIdUserType implements UserType {
    protected static final int SQLTYPE = java.sql.Types.INTEGER;

    @Override
    public Object assemble(final Serializable cached, final Object owner) {
        return cached;
    }

    @Override
    public Object deepCopy(final Object o) {
        return o == null ? null : new DemoId(((DemoId) o).id());
    }

    @Override
    public Serializable disassemble(final Object o) {
        return (Serializable) o;
    }

    @Override
    public boolean equals(final Object x, final Object y) {
        return x == null ? y == null : x.equals(y);
    }

    @Override
    public int hashCode(final Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return original;
    }

    @Override
    public Class<DemoId> returnedClass() {
        return DemoId.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] {SQLTYPE};
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        var id = rs.getBigDecimal(names[0]);
        if (id == null) {
            return null;
        }
        return new DemoId(id.intValue());
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, SQLTYPE);
        } else {
            st.setInt(index, ((DemoId) value).id());
        }
    }

}
