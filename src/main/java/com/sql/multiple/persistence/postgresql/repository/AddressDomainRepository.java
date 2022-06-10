package com.sql.multiple.persistence.postgresql.repository;

import org.springframework.data.repository.CrudRepository;

import com.sql.multiple.persistence.postgresql.entity.AddressDomain;

public interface AddressDomainRepository extends CrudRepository<AddressDomain, Long> {

}
