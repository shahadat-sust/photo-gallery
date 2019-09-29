package com.example.photogallery.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.photogallery.entity.PhotoEntity;

public interface PhotoRepository extends PagingAndSortingRepository<PhotoEntity, Integer> {

}
