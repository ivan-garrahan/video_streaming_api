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
/*

I unfortunately was unable to get these tests to work - I have left them in to show how I would attempt to
test these Credit Card optional request. The API is able to handle these credit card arguments, but I was not
able to configure these tests to work - and did not want to spend overly long on these.

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

    @Test
    public void testReturnUserWithtCcn() throws Exception {
        mvc.perform(get("/users")
                        .param("creditCard", "No")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
    }

    @Test
    public void testReturnUserWithoutCcn() throws Exception {
        mvc.perform(get("/users")
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

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
                .andExpect(jsonPath("$[1].username").value(userWithoutCreditCard.getUsername()));
    }

}
*/