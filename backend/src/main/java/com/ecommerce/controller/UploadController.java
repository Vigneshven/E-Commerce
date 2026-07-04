package com.ecommerce.controller;

import com.ecommerce.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String url = fileStorageService.storeFile(file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
