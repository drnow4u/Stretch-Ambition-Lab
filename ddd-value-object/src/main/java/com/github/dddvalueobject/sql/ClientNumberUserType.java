package com.github.dddvalueobject.sql;

import com.github.dddvalueobject.ClientNumber;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ClientNumberUserType implements UserType {
    protected static final int SQLTYPE = Types.VARCHAR;

    @Override
    public Object assemble(final Serializable cached, final Object owner) {
        return cached;
    }

    @Override
    public Object deepCopy(final Object o) {
        return o == null ? null : new ClientNumber(((ClientNumber) o).value());
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
    public Class<ClientNumber> returnedClass() {
        return ClientNumber.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{SQLTYPE};
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        var id = rs.getString(names[0]);
        if (id == null) {
            return null;
        }
        return new ClientNumber(id);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, SQLTYPE);
        } else {
            st.setString(index, ((ClientNumber) value).value());
        }
    }

}
