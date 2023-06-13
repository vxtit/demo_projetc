package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
//	@Autowired
//	TwilioService twilioService;
	
	@PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        String verificationCode = userService.add(userDTO);
        return ResponseEntity.ok("Verification code: " + verificationCode);
    }
	
	@GetMapping("/display-user")
	public List<User> getAll() {
		return userService.getAllUser();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		userService.update(id, userDTO);
		return new ResponseEntity<>("update successful", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.delete(id);
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<UserDTO> get(@PathVariable Long id){
		return Optional.of(new ResponseEntity<UserDTO>(userService.getOne(id), HttpStatus.OK))
				.orElse(new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String phoneNumber, @RequestParam String verificationCode) {
        boolean verified = userService.verify(phoneNumber, verificationCode);
        if (verified) {
            return ResponseEntity.ok("User verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }
    }
	
	@PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody UserDTO userDTO) {
        try {
            userService.updatePassword(userDTO);
            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
