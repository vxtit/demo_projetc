package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;

public interface CategoryService {
	
	List<Category> filterCategory(CategoryDTO catDTO);
	
	void add(CategoryDTO catDTO);

	CategoryDTO delete(Long id);

	List<Category> getAll();
	
	List<Category> getAllMemory();
	
	void update(Long id, CategoryDTO catDTO);
	
	CategoryDTO getOne(Long id);

}
