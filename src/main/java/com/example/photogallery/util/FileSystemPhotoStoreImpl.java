package com.example.photogallery.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class FileSystemPhotoStoreImpl implements PhotoStore {

	@Autowired
	private ServletContext context; 
	
	@Value("${photo.file.system.store.directory}")
	private String photoUploadDirectory;
	
	@Value("${photo.file.system.read.url}")
	private String photoReadUrl;
	
	@Override
	public String store(CommonsMultipartFile multipartFile) throws Exception {
		String fileName = System.currentTimeMillis() 
    			+ multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."), 
    					multipartFile.getOriginalFilename().length());
		
		String uploadDir = context.getRealPath("") + this.photoUploadDirectory + File.separator;
		System.out.println("************* uploadDir => " + uploadDir);
		File uploadDirFile = new File(uploadDir);
		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdirs();
		}
		
		String uploadFullPath = uploadDir + fileName;
		System.out.println("************* uploadFullPath => " + uploadFullPath);

		File uploadFullPathFile = new File(uploadFullPath);
		FileOutputStream outputStream = new FileOutputStream(uploadFullPathFile);
		outputStream.write(multipartFile.getBytes());
		outputStream.close();
		
		return fileName;
	}
	
	@Override
	public void delete(String url) {
		String uploadedDir = context.getRealPath("") + this.photoUploadDirectory + File.separator;
		System.out.println("************* uploadedDir => " + uploadedDir);
		File file = new File(uploadedDir);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public String getStoredUrl(String url) {
		return MessageFormat.format(photoReadUrl, context.getContextPath(), url);
	}

}
