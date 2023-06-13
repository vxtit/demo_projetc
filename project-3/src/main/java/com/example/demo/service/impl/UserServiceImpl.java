package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;

@Transactional

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

//	@Autowired
//	TwilioService twilioService;

//	private String generateVerificationCode() {
		// Tạo mã xác nhận ngẫu nhiên gồm 5 chữ số
//		Random random = new Random();
//		int verificationCode = random.nextInt(90000) + 10000;
//		return String.valueOf(verificationCode);
//	}

//	@Override
//	public boolean verifyCode(String phoneNumber, String verificationCode) {
//		Optional<User> optionalUser = userRepo.findByPhonenumber(phoneNumber);
//		if (optionalUser.isPresent()) {
//			User user = optionalUser.get();
//			if (verificationCode.equals(user.getVerificationCode())) {
//				// Cập nhật thông tin tài khoản của người dùng sau khi xác minh thành công
//				// Ví dụ: user.setVerified(true); userRepository.save(user);
//				userRepo.save(user);
//				return true;
//			}
//		}
//		return false;
//
//	}
	
	 @Autowired
	    private TwilioService twilioService;

	    // ...
	 @Override
	    public String generateVerificationCode() {
	        // Triển khai logic để tạo mã xác nhận
	        // Ví dụ: Tạo một chuỗi ngẫu nhiên gồm 6 chữ số
	        Random random = new Random();
	        int code = 100000 + random.nextInt(900000);
	        return String.valueOf(code);
	    }

	    @Override
	    public String sendVerificationCode(String phoneNumber) {
	        // Tạo mã xác nhận
	        String verificationCode = generateVerificationCode();

	        // Gửi tin nhắn chứa mã xác nhận đến số điện thoại của người dùng
	        twilioService.sendSMS(phoneNumber, "Verification code: " + verificationCode);

	        return verificationCode;
	    }
	
	@Override
	public String add(UserDTO dto) {
		if (dto.getPhonenumber().length() != 12) {
			throw new IllegalArgumentException("Số điện thoại không đúng định dạng. Vui lòng nhập 10 số.");
		}
//		String phoneNumberPattern = "^(086|096|097|098|039|038|037|036|035|034|032|033"
//				+ "|091|094|088|083|084|085|081|082|070|079|077|076|078|089|090|093|092"
//				+ "|052|056|058|099|059|087)\\d{7}$";
//	    if (!dto.getPhonenumber().matches(phoneNumberPattern)) {
//	        throw new IllegalArgumentException("Số điện thoại không đúng định dạng");
//	    }
		String verificationCode = generateVerificationCode();
		List<Role> roles = new ArrayList<>();
		for (Integer r : dto.getUserRoles()) {
			Role role = roleRepo.findById(r).get();
			roles.add(role);
		}
		User u = User.builder().fullname(dto.getFullname()).username(dto.getUsername()).password(dto.getPassword())
				.fullname(dto.getFullname()).address(dto.getAddress()).email(dto.getEmail())
				.phonenumber(dto.getPhonenumber()).userRoles(roles).build();
		u.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
		u.setPersisted(false);
//		u.setVerificationCode(verificationCode);
//		u.setVerificationCode(verificationCode); // Lưu mã xác nhận vào đối tượng User
//		 User savedUser = userRepo.save(u);

		twilioService.sendSMS(dto.getPhonenumber(), "Verification code: " + verificationCode);
		u.setVerificationCode(verificationCode);
		return verificationCode;
	}

	@Override
	public void update(Long id, UserDTO dto) {
		List<Role> roles = new ArrayList<>();
		for (Integer r : dto.getUserRoles()) {
			Role role = roleRepo.findById(r).get();
			roles.add(role);
		}
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy id tài khoản"));
		user.setFullname(dto.getFullname());
		user.setUsername(dto.getUsername());
		user.setAddress(dto.getAddress());
		user.setEmail(dto.getEmail());
		user.setPhonenumber(dto.getPhonenumber());
		user.setUserRoles(roles);

		userRepo.save(user);

	}

	@Override
	public void updatePassword(UserDTO userDTO) {
		User user = userRepo.findByUsername(userDTO.getUsername());

		if (user == null) {
			throw new RuntimeException("Không tìm thấy tài khoản");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
			throw new RuntimeException("Mật khẩu cũ không đúng");
		}
		user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
		userRepo.save(user);
	}

	@Override
	public void delete(Long id) {
		userRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy id tài khoản"));
		userRepo.deleteById(id);

	}

	@Override
	public UserDTO getOne(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy id tài khoản"));
		UserDTO userDTO = UserDTO.builder().fullname(user.getFullname()).username(user.getUsername())
				.address(user.getAddress()).phonenumber(user.getPhonenumber()).email(user.getEmail()).build();
		List<Integer> userRoleIds = user.getUserRoles().stream().map(Role::getId).collect(Collectors.toList());
		userDTO.setUserRoles(userRoleIds);
		return userDTO;
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.getAllUser();
	}

	@Override
	public List<User> filterUser(UserDTO userDTO) {
		return userRepo.filterUser(userDTO);
	}

	@Override
	public boolean verify(String phoneNumber, String verificationCode) {
	    Optional<User> optionalUser = userRepo.findByPhonenumber(phoneNumber);
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        if (user.getVerificationCode() != null && user.getVerificationCode().equals(verificationCode)) {
	            user.setVerified(true);
	            
	            userRepo.save(user);
	            
	            return true;
	        }
	    }
	    return false;
	}


	@Override
	public boolean verifyCode(String phoneNumber, String verificationCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
