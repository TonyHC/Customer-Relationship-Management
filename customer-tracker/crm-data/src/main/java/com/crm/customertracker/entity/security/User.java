package com.crm.customertracker.entity.security;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;

	@Lob
	private Byte[] image;
	
	// CascadeType.MERGE because Role already exists in DB, and we're trying to insert already persisted Role
	// (Role is automatically merged instead)
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
				"id = " + id + ", " +
				"username = " + username + ", " +
				"password = " + password + ", " +
				"firstName = " + firstName + ", " +
				"lastName = " + lastName + ", " +
				"email = " + email + ", " +
				"image = " + Arrays.toString(image) + ")";
	}
}