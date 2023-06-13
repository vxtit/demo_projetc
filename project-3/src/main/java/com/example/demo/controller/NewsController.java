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

import com.example.demo.dto.NewsDTO;
import com.example.demo.entity.News;
import com.example.demo.service.NewsService;

@RestController
@RequestMapping("/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addNews(@RequestBody NewsDTO newsDTO){
		newsService.add(newsDTO);
		return new ResponseEntity<>("create successful", HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public List<News> search(@RequestBody NewsDTO newsDTO){
		return newsService.filterNews(newsDTO);
	}
	
	@GetMapping("/getAll")
	public List<News> getAllNews(){
		return newsService.getAllNews();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody NewsDTO newsDTO){
		newsService.update(id, newsDTO);
		return new ResponseEntity<>("update successful", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		newsService.delete(id);
		return new ResponseEntity<>("delte successful", HttpStatus.OK);
	}
	
	@GetMapping("/getOne/{id}")
    public ResponseEntity<NewsDTO> get(@PathVariable Long id){
        return Optional.of(new ResponseEntity<NewsDTO>(newsService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<NewsDTO>(HttpStatus.NOT_FOUND));
    }
}
