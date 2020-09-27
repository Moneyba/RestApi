package com.theam.rest.api.service.impl;

import com.theam.rest.api.exception.InvalidFieldException;
import com.theam.rest.api.exception.NotFoundException;
import com.theam.rest.api.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${upload-folder}")
    private String uploadFolder;

    @Override
    public UrlResource loadFile(String fileName) throws MalformedURLException {
        String fixedFileName = fileName.replace(" ", "_");
        Path path =  Paths.get(uploadFolder).resolve(fixedFileName);
        UrlResource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new NotFoundException("Image not found");
        }
    }

    @Override
    public String storageFile(MultipartFile file) throws IOException {
        this.checkIfFileIsEmpty(file);
        this.createUploadFolderIfDoesntExist();
        return this.saveFile(file);
    }

    private void createUploadFolderIfDoesntExist() throws IOException{
        if(!Files.exists(Paths.get(uploadFolder))){
            Files.createDirectory(Paths.get(uploadFolder));
        }
    }

    private void checkIfFileIsEmpty(MultipartFile file) {
        if(file.isEmpty()) throw new InvalidFieldException("The file is empty");
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
        byte[] bytes = file.getBytes();
        Path path =  Paths.get(uploadFolder).resolve(fileName);
        if(!Files.exists(path)) Files.write(path, bytes);
        return fileName;
    }
}




