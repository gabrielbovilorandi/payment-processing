package com.gabriellorandi.paymentprocessing.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriellorandi.paymentprocessing.transaction.application.dto.CreateTransactionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = "classpath:script/initial-insert.sql")
public class TransactionIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({"21bc029a-f48d-4ed0-8666-94c150c5ff94,1,123.12", "21bc029a-f48d-4ed0-8666-94c150c5ff94,2,23.12",
            "21bc029a-f48d-4ed0-8666-94c150c5ff94,3,123.12", "21bc029a-f48d-4ed0-8666-94c150c5ff94,4,123.12"})
    @DisplayName("It should create new transaction.")
    void createNewAccount(UUID accountId, Integer operationTypeId, BigDecimal amount) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateTransactionRequest request = new CreateTransactionRequest(accountId, operationTypeId, amount);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/transactions")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.accountId", equalTo(accountId.toString())))
                .andExpect(jsonPath("$.operationTypeId", equalTo(operationTypeId)))
                .andExpect(jsonPath("$.amount").exists())
                .andExpect(jsonPath("$.eventDate").exists());
    }

    @ParameterizedTest
    @CsvSource({"7cd83bda-8246-4239-b893-e331b0ce64a2,1,123.12", "bd9198f5-cc26-43eb-b5c5-ae619e1c4b18,2,23.12",
            "72dfdeae-c377-4309-a84b-16ff3a31bee6,3,123.12", "b3c97fb6-923c-4ac1-bdbd-eda7c1b8eb3c,4,123.12"})
    @DisplayName("It should return BadRequest if account is invalid.")
    void validAccount(UUID accountId, Integer operationTypeId, BigDecimal amount) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateTransactionRequest request = new CreateTransactionRequest(accountId, operationTypeId, amount);
        String expectedErrorMessage = "accountId: Account does not exist.";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/transactions")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));
    }

    @ParameterizedTest
    @CsvSource({"21bc029a-f48d-4ed0-8666-94c150c5ff94,5,123.12", "21bc029a-f48d-4ed0-8666-94c150c5ff94,6,23.12",
            "21bc029a-f48d-4ed0-8666-94c150c5ff94,0,123.12", "21bc029a-f48d-4ed0-8666-94c150c5ff94,7,123.12"})
    @DisplayName("It should return BadRequest if operation type is invalid.")
    void validOperationType(UUID accountId, Integer operationTypeId, BigDecimal amount) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateTransactionRequest request = new CreateTransactionRequest(accountId, operationTypeId, amount);
        String expectedErrorMessage = "operationTypeId: Operation type does not exist.";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/transactions")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));
    }

    @ParameterizedTest
    @CsvSource({"21bc029a-f48d-4ed0-8666-94c150c5ff94,1,-123.12", "21bc029a-f48d-4ed0-8666-94c150c5ff94,2,-23.12",
            "21bc029a-f48d-4ed0-8666-94c150c5ff94,3,-123.12", "21bc029a-f48d-4ed0-8666-94c150c5ff94,4,-123.12"})
    @DisplayName("It should return BadRequest if amount is negative.")
    void validAmount(UUID accountId, Integer operationTypeId, BigDecimal amount) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateTransactionRequest request = new CreateTransactionRequest(accountId, operationTypeId, amount);
        String expectedErrorMessage = "amount: Amount must be positive.";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/transactions")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));
    }

    @Test
    @DisplayName("It should return all transactions with custom pagination.")
    void findAllWithCustomPagination() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/transactions")
                .param("page", "0")
                .param("size", "2")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", equalTo(true)))
                .andExpect(jsonPath("$.pageSize", equalTo(2)))
                .andExpect(jsonPath("$.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.totalElements", equalTo(4)))
                .andExpect(jsonPath("$.items[0].transactionId", equalTo("fafcf7d6-5d03-42d9-a956-7d4f2299ed11")))
                .andExpect(jsonPath("$.items[0].accountId", equalTo("21bc029a-f48d-4ed0-8666-94c150c5ff94")))
                .andExpect(jsonPath("$.items[0].operationTypeId", equalTo(1)))
                .andExpect(jsonPath("$.items[0].amount", equalTo(123.45)))
                .andExpect(jsonPath("$.items[0].eventDate", equalTo("2022-06-17T10:00:00Z")))
                .andExpect(jsonPath("$.items[1].transactionId", equalTo("e15502ef-8543-41af-acdb-223d7ed10b89")))
                .andReturn();

    }

}
