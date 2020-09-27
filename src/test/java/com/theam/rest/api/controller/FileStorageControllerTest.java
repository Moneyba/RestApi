package com.theam.rest.api.controller;

import com.theam.rest.api.message.ResponseMessage;
import com.theam.rest.api.service.FileStorageService;
import com.theam.rest.api.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FileStorageController.class)
@AutoConfigureMockMvc(addFilters = false)
class FileStorageControllerTest {
    private static final String FILE_STORAGE_ENDPOINT = "/api/file";

    @Autowired
    private MockMvc mockMvc;

    @Qualifier("userDetailsServiceImpl")
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private FileStorageService fileStorageService;


    @Test
    void whenUploadAFile_thenResponseMessageIsReturned() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file", "image.png", "multipart/form-data", "some_image".getBytes());
        when(fileStorageService.storageFile(any(MultipartFile.class))).thenReturn("fileName");
        ResponseMessage responseMessage = new ResponseMessage("Uploaded the file successfully: fileName");

        mockMvc.perform(MockMvcRequestBuilders.multipart(FILE_STORAGE_ENDPOINT)
                .file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(responseMessage));
    }


    @Test
    void whenLoadAFile_then200IsReturned() throws Exception {
        String mockFile = "This is my image";
        InputStream is = new ByteArrayInputStream(mockFile.getBytes());
        UrlResource mockResource = Mockito.mock(UrlResource.class);

        when(mockResource.getInputStream()).thenReturn(is);
        when(fileStorageService.loadFile("fileName")).thenReturn(mockResource);

        mockMvc.perform(MockMvcRequestBuilders.get(FILE_STORAGE_ENDPOINT + "/fileName")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }
}




