package com.example.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.service.PdfGenerateService;

@RestController
@RequestMapping("/pdf")
public class PdfFileController {

	@Autowired
	private PdfGenerateService pdfService;

	

	@PostMapping("/create")
	public ResponseEntity<String> createPdfFile(@RequestParam(value = "textData") String data) {
		try {
			String newFileName = System.currentTimeMillis() + "_" + "Generated.pdf";
			pdfService.generateAndStorePDF(data, newFileName);
			return ResponseEntity.ok("Successfully Pdf is created " + newFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.internalServerError().body("Something went wrong");

	}

}
