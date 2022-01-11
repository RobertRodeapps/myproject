package com.problem1.myproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.problem1.myproject.model.RolesEnum;
import com.problem1.myproject.model.User;
import com.problem1.myproject.service.implementation.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void getAll() throws Exception {
        mockMvc.perform(get("/menu/users")
                .contentType("application/json"))
                .andExpect(status().isOk());
         mockMvc.perform(get("/menu/users/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
      /*  mockMvc.perform(get("/menu/users/4")
                .contentType("application/json"))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})

    void addUser() throws Exception {
        User userMock = new User(123,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);

        mockMvc.perform(post("/menu/users")  //somthing with the password its null ??
                .content(objectMapper.writeValueAsString(userMock))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void updateUser() throws Exception {
        User userMock = new User(123,"ben","ben.com","1234",null,12.3,null, RolesEnum.ADMIN);

        mockMvc.perform(put("/menu/users/15")
                .content(objectMapper.writeValueAsString(userMock))
                .contentType("application/json"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/menu/users/15")
                .content(objectMapper.writeValueAsString(userMock))
                .contentType("application/json"))
                .andExpect(status().isMethodNotAllowed());
        /*mockMvc.perform(put("/menu/users/111")
                .content(objectMapper.writeValueAsString(userMock))
                .contentType("application/json"))
                .andExpect(status().isOk());*/
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/menu/users/14") ///Change id
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void getPortofolio() throws Exception {
        mockMvc.perform(get("/menu/users/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

}