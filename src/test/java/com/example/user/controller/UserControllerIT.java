package com.example.user.controller;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.example.TestConstants.*;
import static com.example.TestUtils.convertDtoToJson;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testing")
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testCreate_ValidRequest_And_NonExistingUsername_ReturnsOk() throws Exception {
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().username(USERNAME).password(PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userCreateRequestDTO))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Transactional
    public void testCreate_ValidRequest_And_ExistingUsername_ReturnsConflict() throws Exception {
        User user = User.builder().username(USERNAME).password(PASSWORD).createdBy(USERNAME).build();

        userRepository.save(user);

        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().username(USERNAME).password(PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userCreateRequestDTO))).andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
    }

    @Test
    @Transactional
    public void testCreate_InvalidRequest_ReturnsBadRequest() throws Exception {
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().username(INVALID_USERNAME).password(PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userCreateRequestDTO))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_ValidRequest_And_ExistingId_ReturnsOk() throws Exception {
        User user = User.builder().username(USERNAME).password(PASSWORD).createdBy(USERNAME).build();

        user = userRepository.save(user);

        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(user.getId()).password(MODIFIED_PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userUpdateRequestDTO))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_ValidRequest_And_NonExistingId_ReturnsNotFound() throws Exception {
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).password(MODIFIED_PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userUpdateRequestDTO))).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_ValidRequest_NotModifiedPassword_ReturnsNotModified() throws Exception {
        User user = User.builder().username(USERNAME).password(PASSWORD).createdBy(USERNAME).build();

        user = userRepository.save(user);

        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(user.getId()).password(PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userUpdateRequestDTO))).andExpect(MockMvcResultMatchers.status().isNotModified()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_InvalidRequest_ReturnsBadRequest() throws Exception {
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).password(INVALID_PASSWORD).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(userUpdateRequestDTO))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    @Transactional
    public void testDelete_ValidId_ReturnsOk() throws Exception {
        User user = User.builder().username(USERNAME).password(PASSWORD).createdBy(USERNAME).build();

        user = userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users?id=" + user.getId())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Transactional
    public void testDelete_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users?id=" + ID)).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    @Transactional
    public void testGet_NullParams_ReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testGet_WithParams_ReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?username=" + USERNAME))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
