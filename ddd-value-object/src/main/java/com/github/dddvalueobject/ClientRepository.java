package com.github.dddvalueobject;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {
}
