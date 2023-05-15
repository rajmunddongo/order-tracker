package onlab.aut.bme.hu.java.service;


import onlab.aut.bme.hu.java.entity.Merchant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileUploadService {

    @Value("${upload.path}")
    private String uploadPath;


    public void uploadFile(MultipartFile file, String name) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String uploadDirectory = "C:/Egyetem/order-tracker/angular/src/assets/pictures/indexpictures";

        Path uploadPath = Path.of(uploadDirectory).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(file.getOriginalFilename());

        // Create the upload directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Copy the file to the upload directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }
    }

