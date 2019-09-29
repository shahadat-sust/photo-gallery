package com.example.photogallery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.photogallery.entity.PhotoEntity;
import com.example.photogallery.repository.PhotoRepository;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private PhotoRepository photoRepository;
	
	@Override
	@Transactional
	public PhotoEntity createPhoto(PhotoEntity photoEntity) {
		return photoRepository.save(photoEntity);

	}

	@Override
	public List<PhotoEntity> getPhotos() {
		return (List<PhotoEntity>) photoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	@Override
	public PhotoEntity getPhoto(int id) {
		Optional<PhotoEntity> photoEntity = photoRepository.findById(id);
		return photoEntity.isPresent() ? photoEntity.get() : null;
	}

}
