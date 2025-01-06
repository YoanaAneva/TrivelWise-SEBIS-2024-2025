package com.example.travelwise.service;

import com.example.travelwise.entity.Image;
import com.example.travelwise.entity.Offer;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.travelwise.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    @Value("${application.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;

    @Autowired
    public ImageService(ImageRepository imageRepository, AmazonS3 s3Client) {
        this.imageRepository = imageRepository;
        this.s3Client = s3Client;
    }

    public void addImageToOffer(MultipartFile imageFile, Offer offer) {
        Image newImage = new Image();
        String imageUrl = uploadImageToBucket(imageFile);
        newImage.setUrl(imageUrl);

        newImage.setOffer(offer);
        offer.getImages().add(newImage);

        imageRepository.save(newImage);
    }

    public String uploadImageToBucket(MultipartFile mPartFile) {
        File imageForUpload = convertMultipartFileToFile(mPartFile);
        String filename = System.currentTimeMillis() + mPartFile.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, filename, imageForUpload));
        imageForUpload.delete();
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
    }

    public void deleteFile(String filepath) {
        if (filepath.contains("/")) {
            String[] filepathParts = filepath.split("/");
            String fileName = filepathParts[filepathParts.length - 1];
            s3Client.deleteObject(bucketName, fileName);
        }
    }

    private File convertMultipartFileToFile(MultipartFile mpartFile) {
        File convertedFile = new File(mpartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(mpartFile.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return convertedFile;
    }
}
