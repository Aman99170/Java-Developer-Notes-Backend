package com.portfolio.fileupload.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.fileupload.entity.FileEntity;
import com.portfolio.fileupload.entity.TopicEntity;
import com.portfolio.fileupload.repository.FileRepository;
import com.portfolio.fileupload.service.FileService;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "${frontend.url}",exposedHeaders = "Content-Disposition")
public class FileController {

  @Autowired
  private FileService fileService;
  @Autowired
  private FileRepository fileRepository;

  @PostMapping(value="/fileUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Object uploadFile(@ModelAttribute FileEntity fileEntity) throws IOException {
	  FileEntity file = fileService.uploadFile(fileEntity);
	  Map<String,String> response = new HashMap<>();
	  response.put("status", "success");
	  response.put("filename", file.getTopic());
	  return response;
    
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
    FileEntity fileEntity = fileService.getFile(id);
    if (fileEntity != null) {
    	fileRepository.updateCount(id);
    	String mimeType = determineMimeType(fileEntity.getOriginalFileName());
      return ResponseEntity.ok()
    		  .contentType(MediaType.parseMediaType(mimeType))
    		  .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getOriginalFileName() + "\"")
    		  .body(fileEntity.getFileBytes());
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  
  @GetMapping("/getAllTopics")
  public List<TopicEntity> getAllTopics() {
	  return fileService.getAllTopics();
  }
  
  
  private String determineMimeType(String fileName) {
	  String mimeType = "application/octet-stream";
	  if (fileName != null) {
	    if (fileName.endsWith(".pdf")) {
	      mimeType = "application/pdf";
	    } else if (fileName.endsWith(".txt")) {
	      mimeType = "text/plain";
	    } else if (fileName.endsWith(".docx")) {
	      mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	    }
	    // Add more conditions for other file types as needed
	  }
	  return mimeType;
	}
  
}