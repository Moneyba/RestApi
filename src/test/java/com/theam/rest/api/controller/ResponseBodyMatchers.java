package com.theam.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theam.rest.api.exception.ExceptionResponse;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyMatchers {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher containsObjectAsJson(Object expectedObject) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            assertThat(json).isEqualToIgnoringWhitespace(
                    objectMapper.writeValueAsString(expectedObject));
        };
    }

    public ResultMatcher containsError(String expectedMessage, String expectedDetail) {
        return mvcResult -> {
            ExceptionResponse expectedError =
                    new ExceptionResponse(expectedMessage, LocalDateTime.now(), Collections.singletonList(expectedDetail));
            String actualResponseBody =
                    mvcResult.getResponse().getContentAsString();
            String expectedResponseBody =
                    objectMapper.writeValueAsString(expectedError);
            assertThat(actualResponseBody)
                    .isEqualToIgnoringWhitespace(expectedResponseBody);
        };

    }


    static ResponseBodyMatchers responseBody(){
        return new ResponseBodyMatchers();
    }

}
