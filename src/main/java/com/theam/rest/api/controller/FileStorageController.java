package com.theam.rest.api.controller;

import com.theam.rest.api.message.ResponseMessage;
import com.theam.rest.api.service.ImageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("api/file")
public class FileStorageController {
    private static final Logger log = LoggerFactory.getLogger(FileStorageController.class);

    private final ImageStorageService imageStorageService;

    public FileStorageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("METHOD FileStorageController.uploadImage");
        String fileName = imageStorageService.storageFile(file);
        String message = "Uploaded the file successfully: " + fileName;
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage(message));
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> loadImage(@PathVariable String fileName) throws MalformedURLException {
        log.info("METHOD FileStorageController.loadImage");
        Resource file = imageStorageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
