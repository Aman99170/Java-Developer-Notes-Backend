package com.portfolio.fileupload.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portfolio.fileupload.entity.FileEntity;
import com.portfolio.fileupload.entity.SubTopicEntity;

import jakarta.transaction.Transactional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

	@Query("SELECT DISTINCT TRIM(topic) FROM FileEntity")
	List<String> getAllTopics();
	
	@Query("SELECT new com.portfolio.fileupload.entity.SubTopicEntity (id,subtopic) FROM FileEntity WHERE topic = :topic")
	List<SubTopicEntity> findSubtopicsByTopic(@Param("topic") String topic);
	
	@Modifying
	@Transactional
	@Query("UPDATE FileEntity SET count = count+1 where id =:id")
	void updateCount(@Param("id") Long id);

}