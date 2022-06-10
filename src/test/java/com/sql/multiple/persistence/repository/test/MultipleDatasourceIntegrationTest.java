package com.sql.multiple.persistence.repository.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sql.multiple.persistence.mysql.entity.Address;
import com.sql.multiple.persistence.mysql.entity.User;
import com.sql.multiple.persistence.mysql.repository.UserRepository;
import com.sql.multiple.persistence.postgresql.entity.AddressDomain;
import com.sql.multiple.persistence.postgresql.entity.UserDomain;
import com.sql.multiple.persistence.postgresql.repository.UserDomainRepository;

@SpringBootTest
@EnableTransactionManagement
class MultipleDatasourceIntegrationTest {

	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void savePostresqlUserTest() {
		AddressDomain address = AddressDomain.builder()
				.city("Los Angeles")
				.country("US")
				.postalCode("90063")
				.state("CA")
				.streetName("Main Street")
				.streetNumber("1000")
				.build();
		
		UserDomain userDomain = UserDomain.builder()
				.firstName("John")
				.lastName("Doe")
				.address(address)
				.phone("xxx-xxx-xxxx")
				.email("john.doe@domain.com")
				.build();
		
		userDomain = userDomainRepository.save(userDomain);
		
		assertNotNull(userDomain);
		assertNotNull(userDomain.getId());
	}
	
	@Test
	void savePostresqlUserTest_noEmail() {
		AddressDomain address = AddressDomain.builder()
				.city("Los Angeles")
				.country("US")
				.postalCode("90063")
				.state("CA")
				.streetName("Main Street")
				.streetNumber("1001")
				.build();
		
		final UserDomain userDomain = UserDomain.builder()
				.firstName("Johnny")
				.lastName("Doer")
				.address(address)
				.phone("xxx-xxx-xxxx")
				.build();
		
		DataIntegrityViolationException dive = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			userDomainRepository.save(userDomain);
		});
		
		assertTrue(dive.getMessage().contains("email"));
	}
	
	@Test
	void saveMySqlUserTest() {
		Address address = Address.builder()
				.city("Los Angeles")
				.country("US")
				.postalCode("90063")
				.state("CA")
				.streetName("Main Street")
				.streetNumber("1000")
				.build();
		
		User user = User.builder()
				.firstName("Jane")
				.lastName("Doe")
				.address(address)
				.phone("xxx-xxx-xxxx")
				.email("jane.doe@domain.com")
				.build();
		
		user= userRepository.save(user);
		
		assertNotNull(user);
		assertNotNull(user.getId());
	}
	
	@Test
	void saveMySqlUserTest_noEmail() {
		Address address = Address.builder()
				.city("Los Angeles")
				.country("US")
				.postalCode("90063")
				.state("CA")
				.streetName("Main Street")
				.streetNumber("1001")
				.build();
		
		User user = User.builder()
				.firstName("Jannine")
				.lastName("Doer")
				.address(address)
				.phone("xxx-xxx-xxxx")
				.build();
		
		DataIntegrityViolationException dive = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			userRepository.save(user);
		});
		
		assertTrue(dive.getMessage().contains("email"));
	}

}
