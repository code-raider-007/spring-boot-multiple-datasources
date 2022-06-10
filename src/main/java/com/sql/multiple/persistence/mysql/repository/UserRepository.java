package com.sql.multiple.persistence.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.sql.multiple.persistence.mysql.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
