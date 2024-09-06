package com.ivan.videostreamingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class CreditCardRequestTests {

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User userWithCreditCard;
    private User userWithoutCreditCard;

    @BeforeEach
    public void setup() {
        userWithCreditCard = new User("userWithCard", "Password123", "withCard@example.com", "1990-01-01", "1234567812345678");
        userWithoutCreditCard = new User("userWithoutCard", "Password123", "withoutCard@example.com", "1990-01-01");
        // Add these users to the UserService's list or mock UserService
    }

//    @Test
//    public void testGet() throws Exception {
//
//        mvc.perform(post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userWithoutCreditCard)));
//
//        MvcResult result = mvc.perform(get("/api/users")
//                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect()
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
//
//
//    }

    @Test
    public void testReturnUserWithCcn() throws Exception {

//        userService.createUser(userWithCreditCard);

        mvc.perform(get("/api/users")
                        .param("creditCard", "Yes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(equalTo("Test")));
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
//                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present

    }

    @Test
    public void testReturnUserWithoutCcn() throws Exception {
        mvc.perform(get("/api/users")
                        .param("creditCard", "No")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithoutCreditCard.getUsername()))
                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
    }

    @Test
    public void testNotFilterReturnAllUsers() throws Exception {

//        userService.createUser(userWithCreditCard);
//        userService.createUser(userWithoutCreditCard);

        mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
                .andExpect(jsonPath("$[1].username").value(userWithoutCreditCard.getUsername()));
    }

}
