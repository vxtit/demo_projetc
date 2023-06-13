package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{
	
	@Query("select n from Category n where"
			+ " ( :#{#param.category} is null or n.category like %:#{#param.category}%)")		
	List<Category> filterCategory(@Param(value = "param") CategoryDTO param);
	
	@Query(value = "SELECT n FROM Category n WHERE n.isDelete = 1 ORDER BY n.id ASC")
	List<Category> getAllCategory();
	
	@Query(value = "SELECT n FROM Category n WHERE n.isDelete = 0 ORDER BY n.id ASC")
	List<Category> getAllMemoryCategory();
}
