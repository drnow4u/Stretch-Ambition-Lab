package com.github.dddvalueobject;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = ClientEntity.TABLE_NAME)
public class ClientEntity {
    public static final String TABLE_NAME = "client";

    public ClientEntity() {
    }

    public ClientEntity(UUID id, DemoId demoId, ClientNumber clientNumber, AccountNumber accountNumber) {

        this.id = id;
        this.demoId = demoId;
        this.clientNumber = clientNumber;
        this.accountNumber = accountNumber;
    }

    @Id
    private UUID id;

    @Column
    @Type(type = "com.github.dddvalueobject.sql.DemoIdUserType")
    private DemoId demoId;

    @NotNull
    @Column
    @Type(type = "com.github.dddvalueobject.sql.ClientNumberUserType")
    private ClientNumber clientNumber;

    @NotNull
    @Column
    @Type(type = "com.github.dddvalueobject.sql.AccountNumberUserType")
    private AccountNumber accountNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DemoId getDemoId() {
        return demoId;
    }

    public void setDemoId(DemoId demoId) {
        this.demoId = demoId;
    }

    public ClientNumber getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(ClientNumber clientNumber) {
        this.clientNumber = clientNumber;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

}
