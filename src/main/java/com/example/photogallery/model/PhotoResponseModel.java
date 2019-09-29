package com.example.photogallery.model;

public class PhotoResponseModel {

	private String caption;
	private String url;
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "PhotoResponseModel [caption=" + caption + ", url=" + url + "]";
	}

}
