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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addCat(@RequestBody CategoryDTO categoryDTO) {
		categoryService.add(categoryDTO);
		return new ResponseEntity<>("create successful", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public List<Category> getAll(){
		return categoryService.getAll();
	}
	
	@GetMapping("/getAllMemory")
	public List<Category> getAllMemory(){
		return categoryService.getAllMemory();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
		categoryService.update(id, categoryDTO);
		return new ResponseEntity<String>("update successful", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		categoryService.delete(id);
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<CategoryDTO> getOne(@PathVariable Long id){
		return Optional.of(new ResponseEntity<CategoryDTO>(categoryService.getOne(id), HttpStatus.OK))
				.orElse(new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("/search")
	public List<Category> search(@RequestBody CategoryDTO catDTO) {
		return categoryService.filterCategory(catDTO);
	}
}
