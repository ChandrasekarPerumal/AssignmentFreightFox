package com.example.rest.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class AwsStorageService {

	@Value("${aws.s3.bucketname}")
	private String s3BucketName;

	@Autowired
	private AmazonS3 s3Client;

	public String uploadFile(MultipartFile file, String folderName) {
		String fileName = null;

		if (s3Client.doesBucketExist(s3BucketName)) {

			File fileObject = convertMultiPartFileToFile(file);
			fileName = file.getOriginalFilename();

			String folder = folderName + "/" + fileName;

			s3Client.putObject(new PutObjectRequest(s3BucketName, folder, fileObject));
			fileObject.delete();

		} else {

			return "Storage bucket does not exist";
		}

		return "File uploaded : " + fileName;
	}

	public Map<Boolean, byte[]> downloadFile(String fileName, String folderName) {

		Map<Boolean, byte[]> responseMap = new HashMap<>();

		try {
			S3Object s3Object = s3Client.getObject(s3BucketName, folderName + "/" + fileName);
			S3ObjectInputStream inputStream = s3Object.getObjectContent();
			byte[] content = IOUtils.toByteArray(inputStream);
			responseMap.put(true, content);

		} catch (AmazonS3Exception e) {

			if (e.getStatusCode() == 404) {
				responseMap.put(false, "Requested file does not exist ".getBytes());
			} else {
				responseMap.put(false, "Please check the input".getBytes());
			}

		} catch (Exception e) {
			responseMap.put(false, e.getMessage().getBytes());
		}

		return responseMap;
	}

	private File convertMultiPartFileToFile(MultipartFile file) {

		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
		return convertedFile;
	}

}
