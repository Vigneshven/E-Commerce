package com.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public String storeFile(MultipartFile file) throws IOException {
        Files.createDirectories(root);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), root.resolve(fileName));
        return "/uploads/" + fileName;
    }
}
