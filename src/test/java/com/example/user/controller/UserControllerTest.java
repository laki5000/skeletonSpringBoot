/*package com.example.user.controller;

import com.example.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.save(entity);
    }

    @Test
    @Transactional
    public void testCreate_ValidRequest_And_NonExistingUsername_ReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(otherCRequest))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Transactional
    public void testCreate_ValidRequest_And_ExistingUsername_ReturnsConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(cRequest))).andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
    }

    @Test
    @Transactional
    public void testCreate_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(invalidCRequest))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_ValidRequest_And_ExistingId_ReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(uRequest))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_ValidRequest_And_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(otherURequest))).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    @Transactional
    public void testUpdate_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(convertDtoToJson(invalidURequest))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
}
*/
