package com.example.travelwise.service;

import com.example.travelwise.entity.Image;
import com.example.travelwise.entity.Offer;
import com.example.travelwise.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class ImageCloudinaryService {
    private final ImageRepository imageRepository;
    @Value("${cloudinary.cloud_name}")
    private String cloudName;
    @Value("${cloudinary.upload_preset}")
    private String uploadPreset;

    @Autowired
    private ImageCloudinaryService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addImageToOffer(MultipartFile imageFile, Offer offer) {
        Image newImage = new Image();
        String imageUrl = "";
        try {
            imageUrl = uploadImage(imageFile);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        newImage.setUrl(imageUrl);
        newImage.setOffer(offer);
        offer.getImages().add(newImage);
        imageRepository.save(newImage);
    }

//  Upload a multipart file to Cloudinary using unsigned preset
    public String uploadImage(MultipartFile file) throws IOException {
        String urlStr = "https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload";
        String boundary = "===Boundary===";

        HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream os = connection.getOutputStream()) {
            // upload_preset
            os.write(("--" + boundary + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"upload_preset\"\r\n\r\n".getBytes());
            os.write((uploadPreset + "\r\n").getBytes());

            // file
            os.write(("--" + boundary + "\r\n").getBytes());
            os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getOriginalFilename() + "\"\r\n").getBytes());
            os.write(("Content-Type: " + file.getContentType() + "\r\n\r\n").getBytes());
            os.write(file.getBytes());
            os.write("\r\n".getBytes());

            os.write(("--" + boundary + "--\r\n").getBytes());
        }

        // Get response
        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        // Parse secure_url from JSON
        return response.split("\"secure_url\":\"")[1].split("\"")[0];
    }

    void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
