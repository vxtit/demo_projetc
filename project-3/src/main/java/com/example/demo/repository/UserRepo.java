package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	@Query("select n from User n where"
			+ " ( :#{#param.fullname} is null or n.fullname like %:#{#param.fullname}%) and"
			+ " ( :#{#param.username} is null or n.username = :#{#param.username}) and"
			+ " ( :#{#param.phonenumber} is null or n.phonenumber = :#{#param.phonenumber}) and"
			+ " ( :#{#param.email} is null or n.email = :#{#param.email}) and"
			+ " ( :#{#param.address} is null or n.address = :#{#param.address}) and"
			+ " ( :#{#param.userRoles} is null or n.userRoles like %:#{#param.userRoles})")
	List<User> filterUser(@Param(value = "param") UserDTO param);
	
	User findByUsername(String username);
	
	Optional<User> findByPhonenumber(String phonenumber);
	
	@Query(value = "SELECT n FROM User n ORDER BY n.id ASC")
	List<User> getAllUser();

}
