package com.itechart.retailers.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User extends Identity {

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "login")
	private String login;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "active")
	private boolean isActive;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	@ToString.Exclude
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	@ToString.Exclude
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@ToString.Exclude
	private Customer customer;

	@JsonIgnore
	@JsonProperty(value = "customer")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<SimpleGrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.role.getRole()));
	}
}
