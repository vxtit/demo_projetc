package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class UserDTO {
	private String fullname;
	private String address;
	private String phonenumber;
	private String username;
	private String email;
	private String password;
	private String newPassword;
	private String verificationCode;
	private List<Integer> userRoles;
}
