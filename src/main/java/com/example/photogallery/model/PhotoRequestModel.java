package com.example.photogallery.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PhotoRequestModel {

	private String caption;
	private CommonsMultipartFile file;
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	
}
