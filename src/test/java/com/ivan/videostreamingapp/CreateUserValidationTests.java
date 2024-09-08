package com.ivan.videostreamingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class CreateUserValidationTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private ObjectMapper objectMapper;

    private User validUser;

    @BeforeEach
    public void setup() {
        validUser = new User("johnDoe", "Password123", "john@example.com",
                "1999-01-21", "1234567812345678");

//        mockUserService.createUser(validUser);

    }

    @Test
    public void testGetUsers() throws Exception {

        User newUser = new User("johnDoe", "Password123", "john@example.com", "1999-01-21", "1234567812345678");

        // Directly modify the list inside the mock, as mocks don't persist data
//        List<User> users = new ArrayList<>();
//        users.add(newUser);

        // Mock the getAllUsers method to return the modified list
//        Mockito.when(mockUserService.getAllUsers()).thenReturn(users);

        // Mock the creation method to add the user to the list
//        Mockito.doAnswer(invocation -> {
//            User user = invocation.getArgument(0);
//            users.add(user);
//            return user;
//        }).when(mockUserService).createUser(Mockito.any(User.class));

//        List<User> users = new ArrayList<>(List.of(validUser));
//        Mockito.when(mockUserService.getAllUsers()).thenReturn(users);

//        String userJson = objectMapper.writeValueAsString(newUser);

        when(mockUserService.createUser(newUser)).thenReturn(newUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated()) // Assuming the response is 200 OK for a successful creation
                .andExpect(jsonPath("$.username").value(newUser.getUsername()))
                .andExpect(jsonPath("$.email").value(newUser.getEmail()))
                .andExpect(jsonPath("$.dob").value(newUser.getDob()))
                .andExpect(jsonPath("$.ccn").value(newUser.getCcn()));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("username", "johnDoe"));
//                .andExpect(jsonPath("$[0].username").value(validUser.getUsername()))
//                .andExpect(jsonPath("$[0].email").value(validUser.getEmail()));

    }

//    @Test
//    public void testGetUsers() throws Exception {
//
//        List<User> users = new ArrayList<>(List.of(validUser));
//        Mockito.when(mockUserService.getAllUsers()).thenReturn(users);
//
//        mockMvc.perform(get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username").value(validUser.getUsername()))
//                .andExpect(jsonPath("$[0].email").value(validUser.getEmail()));
//
//    }

    @Test
    public void testGetUserById() throws Exception {

//        List<User> users = new ArrayList<>(List.of(validUser));
        when(mockUserService.getUserById(validUser.getId())).thenReturn(Optional.ofNullable(validUser));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(validUser.getUsername()))
                .andExpect(jsonPath("$[0].email").value(validUser.getEmail()));

    }

    @Test
    public void testEnterValidUser() throws Exception {
        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testEnterNoUsername() throws Exception {
        validUser.setUsername(null);

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidUsername() throws Exception {
        validUser.setUsername("username with spaces");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
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
//        mvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validUser)));
//
//        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(clashingUser);
//
//        mvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(clashingUser)))
//                .andExpect(status().isConflict());
//    }



    @Test
    public void testEnterNoPassword() throws Exception {
        validUser.setPassword(null);

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidPassword() throws Exception {
        validUser.setPassword("invalid password");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidPasswordNoUpper() throws Exception {
        validUser.setPassword("passwordnoupper1");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidPasswordNoNumber() throws Exception {
        validUser.setPassword("passwordNoNumber");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterNoEmail() throws Exception {
        validUser.setEmail(null);

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidEmail() throws Exception {
        validUser.setEmail("invalid email");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterNoDob() throws Exception {
        validUser.setDob(null);

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidDob() throws Exception {
        validUser.setDob("21-01-1999");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenDobIsUnder18_thenReturns400() throws Exception {

        User underageUser = new User("underageUser", "Password123", "underage@example.com", "2010-01-01", "8765432187654321");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(underageUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testEnterInvalidCcnLength() throws Exception {
        validUser.setCcn("1234");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEnterInvalidCcnValue() throws Exception {
        validUser.setCcn("abc");

        when(mockUserService.createUser(Mockito.any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
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
//        MvcResult result = this.mockMvc.perform(get("/users"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String json = result.getResponse().getContentAsString();
//        System.out.println(json);
////        Article article = objectMapper.readValue(json, Article.class);
//
////        mvc.perform(post("/users")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(userWithCreditCard)));
//
//
////        mvc.perform(get("/users")
//////                        .param("creditCard", "Yes")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(userWithCreditCard)))
//////                .andExpect(content().string(""));
////                .andExpect(jsonPath("$.id")is(1));
////                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
////                .andExpect(jsonPath("$[1]").doesNotExist()); // No other users should be present
//
////        String response = mvc.perform(get("/users")
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
//        mockMvc.perform(get("/users")
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
//        mockMvc.perform(get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username").value(userWithCreditCard.getUsername()))
//                .andExpect(jsonPath("$[1].username").value(userWithoutCreditCard.getUsername()));
//    }






}
