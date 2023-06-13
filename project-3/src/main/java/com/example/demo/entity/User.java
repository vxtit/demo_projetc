package com.example.demo.entity;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tbl_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, name = "FULLNAME")
	private String fullname;
	
	@Column(nullable = false, name = "ADDRESS")
	private String address;
	
	@Column(nullable = false, name = "PHONENUMBER")
	private String phonenumber;
	
	@Column(nullable = false, unique = true, name = "EMAIL")
	private String email;

	@Column(unique = true, nullable = false, name = "USERNAME")
	private String username;

	@Column(nullable = false, name = "PASSWORD")
	private String password;
	
	@Column(name = "VERIFIED")
    private boolean verified;
	
	@Transient
    private boolean persisted;
	
	@Column(name = "VERIFICATION_CODE")
    private String verificationCode;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "User_Role", joinColumns = {@JoinColumn(name = "user_id")},
	inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private List<Role> userRoles;
}
