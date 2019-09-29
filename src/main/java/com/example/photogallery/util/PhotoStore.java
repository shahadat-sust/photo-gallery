package com.example.photogallery.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface PhotoStore {

	public String store(CommonsMultipartFile multipartFile) throws Exception;
	
	public void delete(String url);
	
	public String getStoredUrl(String url);
	
}
