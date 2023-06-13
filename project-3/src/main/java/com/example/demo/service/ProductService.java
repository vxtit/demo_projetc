package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;

public interface ProductService {
	
	List<Product> filterProduct(ProductDTO productDTO);

	void add(ProductDTO productDTO);

	void update(Long id, ProductDTO productDTO);

	ProductDTO delete(Long id);
	
	List<Product> getAllProduct(); 
	
	List<Product> getAllMemoryProduct();
	 
	ProductDTO getOne(Long id);

}
