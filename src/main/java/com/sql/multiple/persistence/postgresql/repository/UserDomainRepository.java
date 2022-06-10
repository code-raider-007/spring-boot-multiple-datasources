package com.sql.multiple.persistence.postgresql.repository;

import org.springframework.data.repository.CrudRepository;

import com.sql.multiple.persistence.postgresql.entity.UserDomain;

public interface UserDomainRepository extends CrudRepository<UserDomain, Long> {

}
