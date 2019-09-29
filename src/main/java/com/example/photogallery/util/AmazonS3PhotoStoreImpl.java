package com.example.photogallery.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class AmazonS3PhotoStoreImpl implements PhotoStore {

	@Value("${aws.s3.bucket}")
	private String awsS3Bucket;

	@Autowired
	private ServletContext context; 
	
	@Value("${upload.temp.directory}")
	private String uploadTempDirectory;
	
	@Value("${photo.aws.s3.read.url}")
	private String photoReadUrl;
	
	private AmazonS3 amazonS3;
	
	public AmazonS3PhotoStoreImpl(@Value("${aws.s3.region}") String awsS3Region) {
		this.amazonS3 = AmazonS3ClientBuilder.standard()
				.withRegion(Region.getRegion(Regions.fromName(awsS3Region)).getName())
				.build();
	}
	
	@Override
	public String store(CommonsMultipartFile multipartFile) throws Exception {
		String fileName = System.currentTimeMillis() 
    			+ multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."), 
    					multipartFile.getOriginalFilename().length());
       
		String uploadTempDir = context.getRealPath("") + this.uploadTempDirectory + File.separator;
		System.out.println("************* fos.write(multipartFile.getBytes()); => " + uploadTempDir);
		File uploadTempDirFile = new File(uploadTempDir);
		if (!uploadTempDirFile.exists()) {
			uploadTempDirFile.mkdirs();
		}
		
		String uploadTempFullPath = uploadTempDir + fileName;
		System.out.println("************* uploadTempFullPath => " + uploadTempFullPath);

		File uploadTempFullPathFile = new File(uploadTempFullPath);
		FileOutputStream outputStream = new FileOutputStream(uploadTempFullPathFile);
		outputStream.write(multipartFile.getBytes());
		outputStream.close();
		
		PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, fileName, uploadTempFullPathFile);
		putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
		this.amazonS3.putObject(putObjectRequest);
		
		uploadTempFullPathFile.delete();
		
		return fileName;
	}
	
	@Override
	public void delete(String url) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.awsS3Bucket, url);
		this.amazonS3.deleteObject(deleteObjectRequest);
	}

	@Override
	public String getStoredUrl(String url) {
		return MessageFormat.format(photoReadUrl, this.awsS3Bucket, url);
	}

}
