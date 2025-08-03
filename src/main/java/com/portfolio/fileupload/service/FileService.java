package com.portfolio.fileupload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.fileupload.entity.FileEntity;
import com.portfolio.fileupload.entity.SubTopicEntity;
import com.portfolio.fileupload.entity.TopicEntity;
import com.portfolio.fileupload.repository.FileRepository;

@Service
public class FileService {

  @Autowired
  private FileRepository fileRepository;

  public FileEntity uploadFile(FileEntity file) throws IOException {
    FileEntity fileEntity = new FileEntity();
    fileEntity.setTopic(file.getTopic());
    fileEntity.setSubtopic(file.getSubtopic());
    fileEntity.setFileBytes(file.getFile().getBytes());
    fileEntity.setOriginalFileName(file.getFile().getOriginalFilename());
    return fileRepository.save(fileEntity);
  }

  public FileEntity getFile(Long id) {
    return fileRepository.findById(id).orElse(null);
  }

  public List<TopicEntity> getAllTopics() {
	List<TopicEntity> res = new ArrayList<>();
	List<String> topics= fileRepository.getAllTopics();
	
	for(String topic : topics) {
		List<SubTopicEntity> subtopics = fileRepository.findSubtopicsByTopic(topic);
		TopicEntity obj = new TopicEntity();
		obj.setTopic(topic);
		obj.setSubtopic(subtopics);
		res.add(obj);
	}
	
	return res;
	
	
  }
}
