package com.sql.multiple.persistence.mysql.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sql.multiple.persistence.postgresql.entity.AddressDomain;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "USER")
@Data
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName; 
	
	@Column(unique = true, nullable = false)
	private String email;
	private String phone;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String firstName, String lastName, String email, String phone, Address address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	
}
