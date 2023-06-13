package com.example.demo.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.service.CategoryService;

@Transactional
@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public void add(CategoryDTO catDTO) {
		catDTO.setIsDelete(true);
		Category category = Category.builder().category(catDTO.getCategory()).isDelete(catDTO.getIsDelete()).build();
		categoryRepo.save(category);
	}

	@Override
	public List<Category> getAll() {
		return categoryRepo.getAllCategory();
	}

	@Override
	public List<Category> getAllMemory() {
		return categoryRepo.getAllMemoryCategory();
	}

	@Override
	public void update(Long id, CategoryDTO catDTO) {
		Category category = categoryRepo.findById(id).orElseThrow(() 
				-> new RuntimeException("new is invalid"));
		category.setCategory(catDTO.getCategory());
		category.setIsDelete(catDTO.getIsDelete());
		categoryRepo.save(category);
	}

	@Override
	public CategoryDTO getOne(Long id) {
		Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("category is invalid"));
		CategoryDTO catDTO = CategoryDTO.builder().category(category.getCategory()).build();
		return catDTO;
	}

	@Override
	public CategoryDTO delete(Long id) {
		Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("new is invalid"));
		category.setIsDelete(false);
		Category deleteCategory = categoryRepo.save(category);
		return convertToDTO(deleteCategory);
	}

	private CategoryDTO convertToDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();

		dto.setCategory(category.getCategory());
		dto.setIsDelete(category.getIsDelete());
		return dto;
	}

	@Override
	public List<Category> filterCategory(CategoryDTO catDTO) {
		return categoryRepo.filterCategory(catDTO);
	}

}
