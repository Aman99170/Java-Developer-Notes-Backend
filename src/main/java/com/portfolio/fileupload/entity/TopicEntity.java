package com.portfolio.fileupload.entity;

import java.util.List;

import lombok.Data;

@Data
public class TopicEntity {
	
	String topic;
	List<SubTopicEntity> subtopic;

}
