package com.gabriellorandi.paymentprocessing.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriellorandi.paymentprocessing.account.application.dto.CreateAccountRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = "classpath:script/initial-insert.sql")
public class AccountIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {"12345678987", "12345654321", "12312312312", "12121212121", "14141414141", "12343231265"})
    @DisplayName("It should create new account if document number is not being used.")
    void createNewAccount(String documentNumber) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateAccountRequest request = new CreateAccountRequest(documentNumber);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.documentNumber", equalTo(documentNumber)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678900", "11111111111", "22222222222", "33333333333", "44444444444", "55555555555"})
    @DisplayName("It should throw AccountAlreadyExistException if creating new account with document number already used.")
    void errorCreatingAccountWithNameThatAlreadyExists(String documentNumber) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateAccountRequest request = new CreateAccountRequest(documentNumber);
        String expectedErrorMessage = "Account already exists with documentNumber " + documentNumber + ".";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "123", "1234567891234567891234567891234567891234597891234567897987654321657987456123"})
    @DisplayName("It should return BadRequest if document number is less than 11 and higher than 11 characters.")
    void nameLength(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateAccountRequest request = new CreateAccountRequest(name);
        String expectedErrorMessage = "Document number must be exactly 11 characters.";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0]", containsString(expectedErrorMessage)));
    }

}
