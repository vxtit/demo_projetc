package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
	@Query("select n from Product n where"
			+ " ( :#{#param.name} is null or n.name like %:#{#param.name}%) and"
			+ " ( :#{#param.price} is null or n.price = :#{#param.price}) and"
			+ " ( :#{#param.status} is null or n.status like %:#{#param.status}) and"
			+ " ( :#{#param.discount} is null or n.discount like %:#{#param.discount}) and"
			+ " ( :#{#param.size} is null or n.size like %:#{#param.size})")
	List<Product> filterProduct(@Param(value = "param") ProductDTO param);
	
	Optional<Product> findById(Long id);
	
	@Query(value = "SELECT n FROM Product n WHERE n.isDelete = 1 ORDER BY n.id ASC")
	List<Product> getAllProduct();
	
	@Query(value = "SELECT n FROM Product n WHERE n.isDelete = 0 ORDER BY n.id ASC")
	List<Product> getAllMemoryProduct();
}
