package com.example.demo.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.NewsDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.News;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.NewsRepo;
import com.example.demo.service.NewsService;

@Transactional
@Service("NewsService")
public class NewsServiceImpl implements NewsService{
	
	@Autowired
	private NewsRepo newsRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public List<News> filterNews(NewsDTO newsDTO) {
		return newsRepo.filterNews(newsDTO);
	}

	@Override
	public void add(NewsDTO newsDTO) {
		Category category = categoryRepo.findById(newsDTO.getCategory())
				.orElseThrow(() -> new RuntimeException("category invalid"));
		News news = News.builder().content(newsDTO.getContent()).status(newsDTO.getStatus())
				.imageUrl(newsDTO.getImageUrl()).title(newsDTO.getTitle()).build();
		news.setCategory(category);
		newsRepo.save(news);	
	}

	@Override
	public void update(Long id, NewsDTO newsDTO) {
		Category category = categoryRepo.findById(newsDTO.getCategory())
				.orElseThrow(() -> new RuntimeException("category invalid"));
		News news = newsRepo.findById(id).orElseThrow(() -> new RuntimeException("news is invalid"));
		news.setContent(newsDTO.getContent());
		news.setImageUrl(newsDTO.getImageUrl());
		news.setStatus(newsDTO.getStatus());
		news.setTitle(newsDTO.getTitle());
		news.setCategory(category);
		newsRepo.save(news);
	}

	@Override
	public void delete(Long id) {
		newsRepo.findById(id).orElseThrow(() -> new RuntimeException("news is invalid"));
		newsRepo.deleteById(id);
	}

	@Override
	public List<News> getAllNews() {
		return newsRepo.getAllNews();
	}

	@Override
	public NewsDTO getOne(Long id) {
		News news = newsRepo.findById(id).orElseThrow(() -> new RuntimeException("news is invalid"));
		NewsDTO newsDTO = NewsDTO.builder()
				.content(news.getContent())
				.title(news.getTitle())
				.imageUrl(news.getImageUrl())
				.status(news.getStatus()).build();
		newsDTO.setCategory(news.getCategory().getId());
		return newsDTO;
	}

}
