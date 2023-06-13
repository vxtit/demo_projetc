package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_product")
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name = "NAME")
	private String name; 
	
	@Column(nullable = false, name = "PRICE")
	private String price;
	
	@Column(nullable = false, name = "DESCRIPTION")
	private String description;
	
	@Column(nullable = false, name = "QUANTITY")
	private String quantity;
	
	@Column(name = "DISCOUNT")
	private String discount;
	
	@Column(name = "PRICECHILD")
	private String pricechild;
	
	@Column(nullable = false, name = "SALEOFF")
    @Check(constraints = "saleoff IN (1, 0)")
    private Boolean saleoff;
	
	@Column(nullable = false, name = "STATUS")
    @Check(constraints = "status IN (1, 0)")
    private Boolean status;
	
	@Column(nullable = false, name = "ISDELETE")
    @Check(constraints = "isDelete IN (1, 0)")
    private Boolean isDelete;
	
	@Column(nullable = false, name = "IMAGEURL")
    private String imageUrl;
	
	@ManyToOne 
	@JoinColumn(nullable = false, name="sizeid", referencedColumnName = "id")
    private Size size;
	
	@ManyToOne 
	@JoinColumn(nullable = false, name="categoryid", referencedColumnName = "id")
    private Category category;
}
