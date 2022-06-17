package com.gabriellorandi.paymentprocessing.operationtype.service;

import com.gabriellorandi.paymentprocessing.operationtype.domain.OperationType;
import com.gabriellorandi.paymentprocessing.operationtype.repository.OperationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class OperationTypeCacheServiceImplTest {

    @InjectMocks
    OperationTypeCacheServiceImpl suite;

    @Mock
    OperationTypeRepository repository;

    @Mock
    OperationType operationType;

    @Captor
    ArgumentCaptor<Integer> captor;

    @BeforeEach
    void setup() {
        when(repository.findById(any())).thenReturn(Optional.of(operationType));
    }

    @Test
    @DisplayName("Find OperationType by id test")
    void findById() {
        Integer userId = 1;

        suite.findById(userId);

        verify(repository).findById(captor.capture());

        assertEquals(captor.getValue(), userId, "Should be the same Id.");
    }

}