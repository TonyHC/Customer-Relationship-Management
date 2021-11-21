package com.crm.customertracker.entity.customer;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[a-zA-Z]+", message = "must contain only characters")
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[a-zA-Z]+", message = "must contain only characters")
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull
	@Size(min = 5, max = 50)
	@Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z0-9]+(.+)[a-zA-Z0-9]+", message = "must follow the format: [x@xxx.xxx]")
	@Column(name = "email")
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
	private List<License> licenses;

	public void addLicense(License license) {
		if (licenses == null)
			licenses = new ArrayList<>();
		
		licenses.add(license);
	}
	
	public void deleteLicenses() {
		for (License license : licenses)
				license.setCustomer(null);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
}