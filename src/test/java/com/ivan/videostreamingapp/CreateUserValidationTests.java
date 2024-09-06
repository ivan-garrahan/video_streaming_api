package com.ivan.videostreamingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class CreateUserValidationTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private ObjectMapper objectMapper;


    private User validUser;

    @BeforeEach
    public void setup() {
        validUser = new User("johnDoe", "Password123", "john@example.com",
                "1999-01-21", "1234567812345678");

    }

    @Test
    public void testEnterValidUser() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testEnterNoUsername() throws Exception {
        validUser.setUsername(null);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidUsername() throws Exception {
        validUser.setUsername("username with spaces");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void testEnterExistingUser() throws Exception {
//
//        User clashingUser = new User("johnDoe", "OtherPass1", "eviltwinjohn@example.com",
//                "1999-01-21", "1234567812345678");
//
//        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);
//
//        mvc.perform(post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validUser)));
//
//        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(clashingUser);
//
//        mvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(clashingUser)))
//                .andExpect(status().isConflict());
//    }



    @Test
    public void testEnterNoPassword() throws Exception {
        validUser.setPassword(null);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidPassword() throws Exception {
        validUser.setPassword("invalid password");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidPasswordNoUpper() throws Exception {
        validUser.setPassword("passwordnoupper1");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidPasswordNoNumber() throws Exception {
        validUser.setPassword("passwordNoNumber");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterNoEmail() throws Exception {
        validUser.setEmail(null);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidEmail() throws Exception {
        validUser.setEmail("invalid email");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterNoDob() throws Exception {
        validUser.setDob(null);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidDob() throws Exception {
        validUser.setDob("21-01-1999");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenDobIsUnder18_thenReturns400() throws Exception {

        User underageUser = new User("underageUser", "Password123", "underage@example.com", "2010-01-01", "8765432187654321");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(underageUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testEnterInvalidCcnLength() throws Exception {
        validUser.setCcn("1234");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidCcnValue() throws Exception {
        validUser.setCcn("abc");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }




//    @Test
//    public void whenFilterIsYes_thenReturnsUsersWithCreditCard() throws Exception {
//
//        User userWithCreditCard = new User("userWithCard", "Password123",
//                "withCard@example.com", "1990-01-01", "1234567812345678");
//
////        userService.createUser(userWithCreditCard);
//        given(userService.getUsersByCreditCardFilter("Yes")).willReturn(Collections.singletonList(userWithCreditCard));
//
//
//        MvcResult result = this.mockMvc.perform(get("/api/users"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String json = result.getResponse().getContentAsString();
//        System.out.println(json);
////        Article article = objectMapper.readValue(json, Article.class);
//
////        mvc.perform(post("/api/users")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(userWithCreditCard)));
//
//
////        mvc.perform(get("/api/users")
//////                        .param("creditCard", "Yes")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(userWithCreditCard)))
//////                .andExpect(content().string(""));
////                .andExpect(jsonPath("$.id")is(1));
////                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
////                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
//
////        String response = mvc.perform(get("/api/users")
//////                        .param("creditCard", "Yes")
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andDo(MockMvcResultHandlers.print()) // Print the result to the console
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(content().string(""));
////                .andReturn()
////                .getResponse()
////                .getContentAsString();
////
////        System.out.println("Response JSON: " + response);
////
//    }
//
//    @Test
//    public void whenFilterIsNo_thenReturnsUsersWithoutCreditCard() throws Exception {
//
//        User userWithoutCreditCard = new User("userWithoutCard", "Password123",
//                "withoutCard@example.com", "1990-01-01");
//
//        userService.createUser(userWithoutCreditCard);
//
//        mockMvc.perform(get("/api/users")
//                        .param("creditCard", "No")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username").value(userWithoutCreditCard.getUsername()))
//                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
//    }
//
//    @Test
//    public void whenNoFilterIsProvided_thenReturnsAllUsers() throws Exception {
//
//        User userWithCreditCard = new User("userWithCard", "Password123", "withCard@example.com", "1990-01-01", "1234567812345678");
//        User userWithoutCreditCard = new User("userWithoutCard", "Password123", "withoutCard@example.com", "1990-01-01", null);
//
//        mockMvc.perform(get("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
//                .andExpect(jsonPath("$[1].username").value(userWithoutCreditCard.getUsername()));
//    }






}
