package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/** Superclass for integration tests. */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIT {
    @Autowired private MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    /**
     * Converts an object to JSON.
     *
     * @param obj the object to convert
     * @return the JSON representation of the object
     * @param <T> the type of the object
     * @throws Exception if an error occurs
     */
    protected <T> String toJson(T obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Converts JSON to an object.
     *
     * @param json the JSON to convert
     * @param clazz the class of the object
     * @return the object
     * @param <T> the type of the object
     * @throws Exception if an error occurs
     */
    protected <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * Converts JSON to a page of objects.
     *
     * @param json the JSON to convert
     * @param clazz the class of the object
     * @return the page of objects
     * @param <T> the type of the object
     * @throws IOException if an error occurs
     */
    protected <T> Page<T> fromJsonToPage(String json, Class<T> clazz) throws IOException {
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<>() {});

        List<T> content =
                objectMapper.convertValue(
                        map.get("content"),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        long totalElements = ((Number) map.get("totalElements")).longValue();
        int size = (int) map.get("size");
        int number = (int) map.get("number");

        Pageable pageable = PageRequest.of(number, size, Sort.unsorted());

        return new PageImpl<>(content, pageable, totalElements);
    }

    /**
     * Performs a POST request and expects the specified status code.
     *
     * @param url the URL to post to
     * @param body the body of the request
     * @param statusCode the expected status code
     * @param clazz the class of the object to return
     * @return the result of the request
     * @param <T> the type of the object to return
     * @throws Exception if an error occurs
     */
    protected <T> T performPostAndExpect(String url, Object body, int statusCode, Class<T> clazz)
            throws Exception {
        return fromJson(
                mockMvc.perform(
                                post(url)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(toJson(body)))
                        .andExpect(status().is(statusCode))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                clazz);
    }

    /**
     * Performs a PUT request and expects the specified status code.
     *
     * @param url the URL to put to
     * @param body the body of the request
     * @param statusCode the expected status code
     * @param clazz the class of the object to return
     * @return the result of the request
     * @param <T> the type of the object to return
     * @throws Exception if an error occurs
     */
    protected <T> T performPutAndExpect(String url, Object body, int statusCode, Class<T> clazz)
            throws Exception {
        return fromJson(
                mockMvc.perform(
                                put(url).contentType(MediaType.APPLICATION_JSON)
                                        .content(toJson(body)))
                        .andExpect(status().is(statusCode))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                clazz);
    }

    /**
     * Performs a DELETE request and expects the specified status code.
     *
     * @param url the URL to delete
     * @param statusCode the expected status code
     * @param clazz the class of the object to return
     * @return the result of the request
     * @param <T> the type of the object to return
     * @throws Exception if an error occurs
     */
    protected <T> T performDeleteAndExpect(String url, int statusCode, Class<T> clazz)
            throws Exception {
        return fromJson(
                mockMvc.perform(delete(url))
                        .andExpect(status().is(statusCode))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                clazz);
    }

    /**
     * Performs a GET request and expects the specified status code.
     *
     * @param url the URL to get
     * @param statusCode the expected status code
     * @param clazz the class of the object to return
     * @return the result of the request
     * @param <T> the type of the object to return
     * @throws Exception if an error occurs
     */
    protected <T> T performGetAndExpect(String url, int statusCode, Class<T> clazz)
            throws Exception {
        return fromJson(
                mockMvc.perform(get(url))
                        .andExpect(status().is(statusCode))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                clazz);
    }
}
