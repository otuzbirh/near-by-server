package com.sb.nearby.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.sb.nearby.util.Constants.Product.UPLOAD_FOLDER_NAME;
import static com.sb.nearby.util.Constants.Product.UPLOAD_PATH;

public class ProductUtil {
    private static final Logger logger = LoggerFactory.getLogger(ProductUtil.class);

    public String saveFile(MultipartFile image){
        try {
            File uploadDirectory = new File(UPLOAD_PATH);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            String originalFilename = image.getOriginalFilename();
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = Path.of(UPLOAD_PATH, uniqueFilename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            this.logger.info("Image uploaded");
            return UPLOAD_FOLDER_NAME + uniqueFilename;
        } catch (IOException e) {
            this.logger.error("Failed to upload image.");
            throw new RuntimeException(e);
        }
    }
}
