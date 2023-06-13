package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
	private String name; 
	private String price;
	private String description;
	private String quantity;
	private String discount;
	private String pricechild;
	private Boolean saleoff;
    private Boolean status;
    private Boolean isDelete;
    private String imageUrl;
    private Long size;
    private Long category;
}
