package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.NewsDTO;
import com.example.demo.entity.News;

@Repository
public interface NewsRepo extends JpaRepository<News, Long>{
	@Query("select n from News n where"
			+ " ( :#{#param.title} is null or n.title like %:#{#param.title}%) and"
			+ " ( :#{#param.category} is null or n.category = :#{#param.category}) and"
			+ " ( :#{#param.content} is null or n.content like %:#{#param.content}) and"
			+ " ( :#{#param.status} is null or n.status like %:#{#param.status})")
	Page<News> searchNews(@Param(value = "param") NewsDTO param, Pageable pageable);
	
	@Query("select n from News n ORDER BY n.id ASC")
	Page<News> getPageAllNews(Pageable pageable);
	
	@Query("select n from News n where"
			+ " ( :#{#param.title} is null or n.title like %:#{#param.title}%) and"
			+ " ( :#{#param.category} is null or n.category = :#{#param.category}) and"
			+ " ( :#{#param.content} is null or n.content like %:#{#param.content}) and"
			+ " ( :#{#param.status} is null or n.status like %:#{#param.status})")
	List<News> filterNews(@Param(value = "param") NewsDTO param);
	
	@Query(value = "SELECT n FROM News n ORDER BY n.id ASC")
	List<News> getAllNews(); 
}
