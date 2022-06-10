package com.sql.multiple.persistence.postgresql.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "ADDRESS_DOMAIN")
@Data
@Builder
public class AddressDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String streetName;
	private String streetNumber;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	
	@OneToOne(mappedBy = "address")
	private UserDomain user;

	public AddressDomain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressDomain(Long id, String streetName, String streetNumber, String city, String state, String country,
			String postalCode, UserDomain user) {
		super();
		this.id = id;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.user = user;
	}
	
	
}
