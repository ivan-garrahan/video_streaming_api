package com.ivan.videostreamingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(PaymentController.class)
public class PaymentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testInvalidRequestNoCardNumber() throws Exception {
        PaymentRequest invalidRequest = new PaymentRequest();
        invalidRequest.setCreditCardNumber(null);
        invalidRequest.setAmount("1234567812345678");

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    public void testInvalidRequestNoCreditCardNumber() throws Exception {
        PaymentRequest invalidRequest = new PaymentRequest();
        invalidRequest.setCreditCardNumber(null);
        invalidRequest.setAmount(null);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    public void whenInvalidRequest_thenReturns400() throws Exception {
        PaymentRequest invalidRequest = new PaymentRequest();
        invalidRequest.setCreditCardNumber("123"); // Invalid length
        invalidRequest.setAmount("100"); // Valid amount

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    public void whenCreditCardNotRegistered_thenReturns404() throws Exception {
        PaymentRequest validRequest = new PaymentRequest();
        validRequest.setAmount("100"); // valid amount
        validRequest.setCreditCardNumber("1234567812345678"); // valid Credit Card Number

        given(paymentService.isCreditCardRegistered(validRequest.getCreditCardNumber())).willReturn(false);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void whenValidRequest_thenReturns201() throws Exception {
        PaymentRequest validRequest = new PaymentRequest();
        validRequest.setCreditCardNumber("1234567812345678");
        validRequest.setAmount("100");

        given(paymentService.isCreditCardRegistered(validRequest.getCreditCardNumber())).willReturn(true);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());;
    }

}
