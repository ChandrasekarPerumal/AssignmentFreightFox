package com.example.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.rest.service.AwsStorageService;

@RestController
@RequestMapping("/s3")
public class AwsStorageController {

	
	
	@Autowired
	private AwsStorageService awsStorageService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file ,@RequestParam("userName") String userName ) {
		if(file.isEmpty() || userName.isEmpty()) {
			return new ResponseEntity<>("Kindly verify Input data",HttpStatus.NOT_ACCEPTABLE);
		}else {
			return new ResponseEntity<>(awsStorageService.uploadFile(file,userName), HttpStatus.OK);
		}
		
	}

	@GetMapping("/download/{userName}/{fileName}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName ,@PathVariable String userName) {
		byte[] data = null;
		boolean status = false;		
		Map<Boolean, byte[]> responseMap = awsStorageService.downloadFile(fileName,userName);

		for (Map.Entry<Boolean, byte[]> entry : responseMap.entrySet()) {
			status = entry.getKey();
			data = entry.getValue();
		}

		ByteArrayResource byteArrayResource = new ByteArrayResource(data);
		
		if(status) {
		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"").body(byteArrayResource);
		}
		else{
			return ((BodyBuilder) ResponseEntity.notFound()).contentLength(data.length).header("Content-type", "application/octet-stream")
					.header("Content-disposition", "attachment; filename=\"" + fileName + "\"").body(byteArrayResource);
		}
	}
}
