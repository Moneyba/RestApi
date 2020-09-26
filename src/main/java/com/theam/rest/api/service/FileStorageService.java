package com.theam.rest.api.service;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileStorageService {
    UrlResource loadFile(String fileName) throws MalformedURLException;
    String storageFile(MultipartFile file) throws IOException;
}
