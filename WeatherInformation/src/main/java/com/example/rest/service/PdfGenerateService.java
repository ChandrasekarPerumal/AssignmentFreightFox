package com.example.rest.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Service
public class PdfGenerateService {

	@Autowired
	private TemplateEngine templateEngine;
	
	public static String PDF_FILE_LOCATION;

	
	public void generateAndStorePDF(String data, String fileName) throws IOException, DocumentException {
		
		Context context = new Context();
		context.setVariable("title", "Dynamic PDF Generation");
		context.setVariable("message", data);

		
		String htmlContent = templateEngine.process("template", context);

		File file = new File("/home/mglocadmin/Documents/pdf_files"+"/"+fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
		document.open();		
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(htmlContent.getBytes()));
		
		writer.flush();
		document.close();
	}
}
