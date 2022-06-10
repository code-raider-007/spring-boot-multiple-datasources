package com.sql.multiple.persistence.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.sql.multiple.persistence.mysql.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
