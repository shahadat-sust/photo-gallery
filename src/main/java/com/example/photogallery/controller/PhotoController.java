package com.example.photogallery.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.photogallery.entity.PhotoEntity;
import com.example.photogallery.model.PhotoRequestModel;
import com.example.photogallery.model.PhotoResponseModel;
import com.example.photogallery.service.PhotoService;
import com.example.photogallery.util.PhotoStore;

@Controller
public class PhotoController {

	@Autowired
	private PhotoService photoService;

	@Autowired
	//@Qualifier("fileSystemPhotoStoreImpl")
	@Qualifier("amazonS3PhotoStoreImpl")
	private PhotoStore photoStore;

	@GetMapping("/")
	public String showPhotoForm(Model model) {
		model.addAttribute("uploadPhotoForm", new PhotoRequestModel());
		return "photoform";
	}
	
	
	@PostMapping("/uploadPhoto")
	public String singleFileUpload(@ModelAttribute("uploadPhotoForm") PhotoRequestModel uploadPhotoForm, BindingResult bindingResult) {

		if (uploadPhotoForm.getFile() == null || uploadPhotoForm.getFile().isEmpty()) {
			bindingResult.rejectValue("file", "error.photo.is.missing");
			return "photoform";
		}

		String tempName = uploadPhotoForm.getFile().getOriginalFilename().toLowerCase();
		if (!(tempName.endsWith(".jpg") || tempName.endsWith(".jpeg") || tempName.endsWith(".png"))) {
			bindingResult.rejectValue("file", "error.send.valid.photo");
			return "photoform";
		}

		if (uploadPhotoForm.getCaption() == null || uploadPhotoForm.getCaption().trim().length() == 0) {
			bindingResult.rejectValue("caption", "error.caption.is.missing");
			return "photoform";
		}

		try {
			String generatedName = photoStore.store(uploadPhotoForm.getFile());
			PhotoEntity photoEntity = new PhotoEntity(); 
			photoEntity.setUrl(generatedName);
			photoEntity.setCaption(uploadPhotoForm.getCaption()); 
			photoService.createPhoto(photoEntity);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.rejectValue("file", "error.failed.to.process.request");
			return "photoform";
		}

		return "redirect:/photoList";
	}

	@GetMapping("/photoList")
	public String getPhotoList(Model model) {
		List<PhotoEntity> photoEntities = photoService.getPhotos();
		
		List<PhotoResponseModel> responseModels = new ArrayList<PhotoResponseModel>();
		for (PhotoEntity photoEntity : photoEntities) {
			PhotoResponseModel responseModel = new PhotoResponseModel();
			responseModel.setCaption(photoEntity.getCaption());
			responseModel.setUrl(photoStore.getStoredUrl(photoEntity.getUrl()));
			responseModels.add(responseModel);
		}
		
		model.addAttribute("photoList", responseModels);
		return "photolist";
	}

}
