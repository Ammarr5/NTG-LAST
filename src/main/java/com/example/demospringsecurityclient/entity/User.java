package com.example.demospringsecurityclient.entity;

import java.util.Set;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name="firstname" ,nullable=false ,length=100)
	private String firstname;
	@Column(name="lastname" ,nullable=false ,length=100)
	private String lastname;
	@Column(name="email" , nullable = false,unique = true)
	private String email;
	@Column(name="password" , nullable = false ,length = 60)
	private String password;
	@Column(name="role" , nullable = false)
    private String role;
	@Column(name="enabled") //enabled column 0 not verified , 1 verified
	private boolean enabled=false;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
//	inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private Set<Role> roles;
}
