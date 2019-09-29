package com.example.photogallery.service;

import java.util.List;

import com.example.photogallery.entity.PhotoEntity;

public interface PhotoService {

	PhotoEntity createPhoto(PhotoEntity photoEntity);
	
	List<PhotoEntity> getPhotos();
	
	PhotoEntity getPhoto(int id);
	
}
