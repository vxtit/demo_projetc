package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewsDTO {
	private Long id;
	private String title; 
	private String content;
    private Boolean status;
    private String imageUrl;
    private Long category;
}
