package com.example.demo.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SizeDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Size;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.SizeRepo;
import com.example.demo.service.ProductService;

@Transactional
@Service("ProductService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private SizeRepo sizeRepo;

	@Override
	public List<Product> filterProduct(ProductDTO productDTO) {
		return productRepo.filterProduct(productDTO);
	}

	@Override
	public void add(ProductDTO productDTO) {
		Category category = categoryRepo.findById(productDTO.getCategory())
				.orElseThrow(() -> new RuntimeException("category invalid"));
		Size size = sizeRepo.findById(productDTO.getSize()).orElseThrow(() -> new RuntimeException("size invalid"));
		int quantity = Integer.parseInt(productDTO.getQuantity());
	    if(quantity == 0) {
	    	productDTO.setStatus(false);
	    } else if (quantity > 0) {
	    	productDTO.setStatus(true);
	    } else {
	    	throw new IllegalArgumentException("Status cannot be negative");
	    }
	    productDTO.setIsDelete(true);
	    if(productDTO.getSaleoff()) {
	    	int discount;
	    	try {
	    		discount = Integer.parseInt(productDTO.getDiscount());
	    	} catch(NumberFormatException e) {
	    		throw new IllegalArgumentException("Invalid discount format");
	    	}
	    	double pricechild = Double.parseDouble(productDTO.getPrice()) - ((discount / 100.0) * Double.parseDouble(productDTO.getPrice()));
	    	
	    	if(discount == 0) {
	    		pricechild = Integer.parseInt(productDTO.getPrice());
	    		productDTO.setSaleoff(false); 
	    	}else if(discount < 0) {
	    		throw new IllegalArgumentException("Discount cannot be negative");
			}
	    	DecimalFormat decimalFormat = new DecimalFormat("0");
	    	String formattedDiscount = decimalFormat.format(discount);
	        String formattedPriceChild = decimalFormat.format(pricechild);
	    	
	    	productDTO.setDiscount(formattedDiscount);
	    	productDTO.setPricechild(formattedPriceChild);
	    }else {
	    	productDTO.setDiscount("0");
	    	productDTO.setPricechild(productDTO.getPrice());
	    }
	    int price = Integer.parseInt(productDTO.getPrice());
	    if (price < 0) {
	    	throw new IllegalArgumentException("Price cannot be negative");
	    }
		Product prd = Product.builder()
				.name(productDTO.getName())
				.price(productDTO.getPrice())
				.description(productDTO.getDescription())
				.quantity(productDTO.getQuantity())
				.status(productDTO.getStatus())
				.isDelete(productDTO.getIsDelete())
				.imageUrl(productDTO.getImageUrl())
				.saleoff(productDTO.getSaleoff())
				.discount(productDTO.getDiscount())
				.pricechild(productDTO.getPricechild())
				.build();
		prd.setCategory(category);
		prd.setSize(size);
		productRepo.save(prd);

	}

	@Override
	public void update(Long id, ProductDTO productDTO) {
		Category category = categoryRepo.findById(productDTO.getCategory())
				.orElseThrow(() -> new RuntimeException("category invalid"));
		Size size = sizeRepo.findById(productDTO.getSize()).orElseThrow(() -> new RuntimeException("size invalid"));
		
		Product prd = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		
		int quantity = Integer.parseInt(productDTO.getQuantity());
	    if(quantity == 0) {
	    	productDTO.setStatus(false);
	    } else if (quantity > 0) {
	    	productDTO.setStatus(true);
	    } else {
	    	throw new IllegalArgumentException("Status cannot be negative");
	    }
	    if(productDTO.getSaleoff()) {
	    	int discount;
	    	try {
	    		discount = Integer.parseInt(productDTO.getDiscount());
	    	} catch(NumberFormatException e) {
	    		throw new IllegalArgumentException("Invalid discount format");
	    	}
	    	double pricechild = Double.parseDouble(productDTO.getPrice()) - ((discount / 100.0) * Double.parseDouble(productDTO.getPrice()));
	    	
	    	if(discount == 0) {
	    		pricechild = Integer.parseInt(productDTO.getPrice());
	    		productDTO.setSaleoff(false); 
	    	}else if(discount < 0) {
	    		throw new IllegalArgumentException("Discount cannot be negative");
			}
	    	DecimalFormat decimalFormat = new DecimalFormat("0");
	    	String formattedDiscount = decimalFormat.format(discount);
	        String formattedPriceChild = decimalFormat.format(pricechild);
	    	
	    	productDTO.setDiscount(formattedDiscount);
	    	productDTO.setPricechild(formattedPriceChild);
	    }else {
	    	productDTO.setDiscount("0");
	    	productDTO.setPricechild(productDTO.getPrice());
	    }
	    int price = Integer.parseInt(productDTO.getPrice());
	    if (price < 0) {
	    	throw new IllegalArgumentException("Price cannot be negative");
	    }
		prd.setName(productDTO.getName());
		prd.setPrice(productDTO.getPrice());
		prd.setDescription(productDTO.getDescription());
		prd.setQuantity(productDTO.getQuantity());
		prd.setStatus(productDTO.getStatus());
		prd.setImageUrl(productDTO.getImageUrl());
		prd.setIsDelete(productDTO.getIsDelete());
		prd.setDiscount(productDTO.getDiscount());
		prd.setPricechild(productDTO.getPricechild());
		prd.setSaleoff(productDTO.getSaleoff());
		prd.setCategory(category);
		prd.setSize(size);
		productRepo.save(prd);

	}

	@Override
	public ProductDTO delete(Long id) {
		Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		product.setIsDelete(false);

		Product deleteProduct = productRepo.save(product);
		return convertToDTO(deleteProduct);
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
	    dto.setName(product.getName());
	    dto.setPrice(product.getPrice());
	    dto.setDescription(product.getDescription());
	    dto.setQuantity(product.getQuantity());
	    dto.setStatus(product.getStatus());
	    dto.setIsDelete(product.getIsDelete());
	    dto.setImageUrl(product.getImageUrl());
	    dto.setDiscount(product.getDiscount());
	    dto.setPricechild(product.getPricechild());
	    dto.setSaleoff(product.getSaleoff());
	    CategoryDTO categoryDTO = new CategoryDTO();
	    categoryDTO.setId(product.getCategory().getId());
	    categoryDTO.setCategory(product.getCategory().getCategory());
	    dto.setCategory(dto.getCategory());
	    SizeDTO sizeDTO = new SizeDTO();
	    sizeDTO.setId(product.getSize().getId());
	    sizeDTO.setSize(product.getSize().getSize());
	    dto.setSize(dto.getSize());
		return dto;
	}

	@Override
	public List<Product> getAllProduct() {
		return productRepo.getAllProduct();
	}

	@Override
	public ProductDTO getOne(Long id) {
		Product prd = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product is invalid"));
		ProductDTO productDTO = ProductDTO
				.builder()
				.name(prd.getName())
				.price(prd.getPrice())
				.description(prd.getDescription())
				.quantity(prd.getQuantity())
				.isDelete(prd.getIsDelete())
				.status(prd.getStatus())
				.imageUrl(prd.getImageUrl())
				.saleoff(prd.getSaleoff())
				.discount(prd.getDiscount())
				.pricechild(prd.getPricechild())
				.build();
		productDTO.setCategory(prd.getCategory().getId());
		productDTO.setSize(prd.getSize().getId());
		return productDTO;
	}

	@Override
	public List<Product> getAllMemoryProduct() {
		return productRepo.getAllMemoryProduct();
	}

}
