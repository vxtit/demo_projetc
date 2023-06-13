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
@Table(name="tbl_news")
@Builder
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, name = "TITLE")
	private String title; 
	@Column(nullable = false, name = "CONTENT")
	private String content;
	@Column(nullable = false, name = "STATUS")
    @Check(constraints = "status IN (1, 2)")
    private Boolean status;
	@Column(nullable = false, name = "IMAGEURL")
    private String imageUrl;
	@ManyToOne 
	@JoinColumn(nullable = false, name="categoryid", referencedColumnName = "id")
    private Category category;
}
