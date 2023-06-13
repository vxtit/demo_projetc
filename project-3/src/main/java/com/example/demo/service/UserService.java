package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;

public interface UserService {
	
	String add(UserDTO dto);

	void update(Long id, UserDTO dto);

	void updatePassword(UserDTO userDTO);

	void delete(Long id);

	UserDTO getOne(Long id);
	
	List<User> getAllUser();
	
	boolean verifyCode(String phoneNumber, String verificationCode);
	
	List<User> filterUser(UserDTO userDTO);

	String generateVerificationCode();
	
	String sendVerificationCode(String phoneNumber); 
	
	boolean verify(String phoneNumber, String verificationCode);
}
