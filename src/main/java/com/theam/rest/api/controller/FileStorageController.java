package com.theam.rest.api.controller;

import com.theam.rest.api.message.ResponseMessage;
import com.theam.rest.api.service.FileStorageService;
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
    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = fileStorageService.storageFile(file);
        String message = "Uploaded the file successfully: " + fileName    ;
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage(message));
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> loadFile(@PathVariable String fileName) throws MalformedURLException {
        Resource file = fileStorageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
