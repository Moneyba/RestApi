package com.theam.rest.api.service.impl;

import com.theam.rest.api.exception.InvalidFieldException;
import com.theam.rest.api.exception.NotFoundException;
import com.theam.rest.api.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    @Value("${upload-folder}")
    private String uploadFolder;

    @Override
    public UrlResource loadFile(String fileName) throws MalformedURLException {
        this.checkItsAUuidString(fileName);
        Path path = Paths.get(uploadFolder).resolve(fileName);
        UrlResource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new NotFoundException("Image not found");
        }
    }

    private void checkItsAUuidString(String fileName) {
        String fileNameWithoutExtension = fileName.split("\\.")[0];
        try {
            UUID.fromString(fileNameWithoutExtension);
        } catch (IllegalArgumentException exception) {
            throw new InvalidFieldException(
                    "Invalid file Name. The file name must be in UUID format");
        }
    }

    @Override
    public String storageFile(MultipartFile file) throws IOException {
        this.checkIfFileIsEmpty(file);
        this.checkIfFileIsAnImage(file);
        this.createUploadFolderIfDoesntExist();
        return this.saveFile(file);
    }

    private void checkIfFileIsEmpty(MultipartFile file) {
        if (file.isEmpty()) throw new InvalidFieldException("The file is empty");
    }

    private void checkIfFileIsAnImage(MultipartFile file) throws IOException {
        InputStream input = file.getInputStream();
        try {
            ImageIO.read(input).toString();
        } catch (Exception e) {
            throw new InvalidFieldException("The file is not a image");
        }
    }

    private void createUploadFolderIfDoesntExist() throws IOException {
        if (!Files.exists(Paths.get(uploadFolder))) {
            Files.createDirectory(Paths.get(uploadFolder));
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String contentType = Objects.requireNonNull(file.getContentType()).split("/")[1];
        String uuidName = UUID.randomUUID().toString() + "." + contentType;

        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadFolder).resolve(uuidName);
        Files.write(path, bytes);
        return uuidName;
    }


}




