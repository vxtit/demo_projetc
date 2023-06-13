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

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
		productService.add(productDTO);
		return new ResponseEntity<>("create successful", HttpStatus.OK);
	}

	@GetMapping("/search")
	public List<Product> search(@RequestBody ProductDTO productDTO) {
		return productService.filterProduct(productDTO);
	}

	@GetMapping("/getAll")
	public List<Product> getAllProduct() {
		return productService.getAllProduct();
	}
	
	@GetMapping("/getMemory")
	public List<Product> getAllMemoryProduct(){
		return productService.getAllMemoryProduct();
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		productService.update(id, productDTO);
		return new ResponseEntity<>("update successful", HttpStatus.OK);
	}

	@GetMapping("/getOne/{id}")
	public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
		return Optional.of(new ResponseEntity<ProductDTO>(productService.getOne(id), HttpStatus.OK))
				.orElse(new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
		ProductDTO deletedProduct = productService.delete(id);
		return ResponseEntity.ok(deletedProduct);
	}
}
