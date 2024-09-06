package com.ivan.videostreamingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class CreateUserValidationTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private ObjectMapper objectMapper;

    private User validUser;

    @BeforeEach
    public void setup() {
//        validUser = new User("johnDoe", "Password123", "john@example.com", "1990-01-01", "1234567812345678");
        validUser = new User("johnDoe", "Password123", "john@example.com",
                "1999-01-21", "1234567812345678");

    }

    @AfterEach
    public void resetUser() {
        validUser = new User("johnDoe", "Password123", "john@example.com",
                "1999-01-21", "1234567812345678");
    }

    @Test
    public void testEnterValidUser() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testEnterInvalidUsername() throws Exception {
        validUser.setUsername(null);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());

    }


}
