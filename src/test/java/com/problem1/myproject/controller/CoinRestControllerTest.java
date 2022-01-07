package com.problem1.myproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.TestUtils;
import com.problem1.myproject.MyprojectApplication;
import com.problem1.myproject.model.Coin;
import com.problem1.myproject.service.implementation.CoinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CoinRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private CoinService coinService;

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getRequestTests() throws Exception {

 /*        Coin mockCoin = new Coin();
        mockCoin.setCoinName("MERGE");
        mockCoin.setId(0);

       coinService.save(mockCoin);

        Coin receivedCoin = coinService.findById(1);
        assertEquals("MERGE",receivedCoin.getCoinName()); */

        mockMvc.perform(get("/menu/coins")
                .contentType("application/json"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/menu/coins/3")
                .contentType("application/json"))
                .andExpect(status().isOk());

        /*mockMvc.perform(get("/menu/coins/100")
            .contentType("application/json"))
            .andExpect(status().isNotFound());*/

      /*  String received = result.getResponse().getContentAsString();
        assertThat(received).isEqualTo(coins.toString());*/
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void addCoin() throws Exception {
        Coin mockCoin = new Coin();
        mockCoin.setCoinName("MER");
        mockCoin.setPrice(1.332);
        mockCoin.setSupply(123.32);
        mockCoin.setId(0);

        mockMvc.perform(post("/menu/coins")
                .content(objectMapper.writeValueAsString(mockCoin))
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void updateCoin() throws Exception {
         Coin mockCoin = new Coin();
        mockCoin.setCoinName("MEr");
        mockCoin.setPrice(1.332);
        mockCoin.setSupply(1231.332);
        mockCoin.setId(3L);


        mockMvc.perform(put("/menu/coins/3")
                .content(objectMapper.writeValueAsString(mockCoin))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    void deleteCoin() throws Exception {

        mockMvc.perform(delete("/menu/coins/3")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}