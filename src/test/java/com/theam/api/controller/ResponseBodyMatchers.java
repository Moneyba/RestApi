package com.theam.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;

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

    public ResultMatcher containsError(String expectedFieldName, String expectedMessage) {
        return mvcResult -> {
            Map<String, String> expectedErrorResponse = new HashMap<>();
            expectedErrorResponse.put(expectedFieldName, expectedMessage);
            String actualResponseBody =
                    mvcResult.getResponse().getContentAsString();
            String expectedResponseBody =
                    objectMapper.writeValueAsString(expectedErrorResponse);
            assertThat(actualResponseBody)
                    .isEqualToIgnoringWhitespace(expectedResponseBody);
        };

    }


    static ResponseBodyMatchers responseBody(){
        return new ResponseBodyMatchers();
    }

}
