package br.com.alelo.consumer.consumerpat.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.service.ConsumerUseCase;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ConsumerController.class)
public class ConsumerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsumerUseCase consumerUseCase;

    private Consumer consumer;

    @BeforeEach
    public void setUp() {
        consumer = new Consumer();
    }

    @Test
    public void testListAllConsumers() throws Exception {
        List<Consumer> consumers = Arrays.asList(new Consumer(), new Consumer());
        when(consumerUseCase.listAllConsumers()).thenReturn(consumers);

        mockMvc.perform(get("/consumer/consumerList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(consumerUseCase).listAllConsumers();
    }

    @Test
    public void testCreateConsumer() throws Exception {
        mockMvc.perform(post("/consumer/createConsumer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Claudio Gomes\",\"documentNumber\":\"123456\"}"))
                .andExpect(status().isOk());

        verify(consumerUseCase).createConsumer(any(Consumer.class));
    }

    @Test
    public void testUpdateConsumer() throws Exception {
        mockMvc.perform(post("/consumer/updateConsumer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Claudio Gomes\",\"documentNumber\":\"123456\"}"))
                .andExpect(status().isOk());

        verify(consumerUseCase).updateConsumer(any(Consumer.class));
    }

    @Test
    public void testSetBalance() throws Exception {
        mockMvc.perform(get("/consumer/setcardbalance")
                        .param("cardNumber", "1234")
                        .param("value", "100.0"))
                .andExpect(status().isOk());

        verify(consumerUseCase).setBalance(1234, 100.0);
    }

    @Test
    public void testBuy() throws Exception {
        mockMvc.perform(get("/consumer/buy")
                        .param("establishmentType", "1")
                        .param("establishmentName", "Company name")
                        .param("cardNumber", "1234")
                        .param("productDescription", "Produto")
                        .param("value", "100.0"))
                .andExpect(status().isOk());

        verify(consumerUseCase).buy(1, "Company name", 1234, "Produto", 100.0);
    }
}
