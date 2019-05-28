package com.github.nenomm.tckts.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nenomm.tckt.lib.TicketRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TicketControllerIntegrationTest {

    private static String BASE_URL = "/tickets/";

    private static String CLIENT_ID = "1";
    private static String COMMON_CLIENT_ID = "common";
    private static String UNKNOWN_CLIENT_ID = "unknown";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTicketForClient() throws Exception {
        MvcResult result = mockMvc
                .perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(constructJsonContent(CLIENT_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticketId").value("ticket-0"))
                .andExpect(jsonPath("$.clientId").value(CLIENT_ID))
                .andReturn();
    }

    @Test
    public void createTicketForCommonClient() throws Exception {
        MvcResult result = mockMvc
                .perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(constructJsonContent(COMMON_CLIENT_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticketId").value("ticket-0"))
                .andExpect(jsonPath("$.clientId").value(COMMON_CLIENT_ID))
                .andReturn();
    }

    @Test
    public void createTicketForUnknownClient() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(constructJsonContent(UNKNOWN_CLIENT_ID))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    private String constructJsonContent(String clientId) throws JsonProcessingException {
        TicketRequest ticketRequest = new TicketRequest(clientId);
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(ticketRequest);
    }
}
