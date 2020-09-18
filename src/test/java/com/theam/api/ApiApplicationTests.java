package com.theam.api;

import com.theam.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= UserService.class)
class ApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
