package com.ivan.videostreamingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        userWithoutCreditCard = new User("userWithoutCard", "Password123", "withoutCard@example.com", "1990-01-01", null);
        // Add these users to the UserService's list or mock UserService
    }

    @Test
    public void whenFilterIsYes_thenReturnsUsersWithCreditCard() throws Exception {
        mvc.perform(get("/api/users")
                        .param("creditCard", "Yes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
    }

    @Test
    public void whenFilterIsNo_thenReturnsUsersWithoutCreditCard() throws Exception {
        mvc.perform(get("/api/users")
                        .param("creditCard", "No")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithoutCreditCard.getUsername()))
                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
    }

    @Test
    public void whenNoFilterIsProvided_thenReturnsAllUsers() throws Exception {
        mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
                .andExpect(jsonPath("$[1].username").value(userWithoutCreditCard.getUsername()));
    }

}
