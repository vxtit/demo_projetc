package com.example.demo.service;

import java.util.List;


import com.example.demo.dto.NewsDTO;
import com.example.demo.entity.News;

public interface NewsService {
	
	List<News> filterNews(NewsDTO newsDTO);

	void add(NewsDTO newsDTO);

	void update(Long id, NewsDTO newsDTO);

	void delete(Long id);
	
	List<News> getAllNews(); 
	 
	NewsDTO getOne(Long id);
}
