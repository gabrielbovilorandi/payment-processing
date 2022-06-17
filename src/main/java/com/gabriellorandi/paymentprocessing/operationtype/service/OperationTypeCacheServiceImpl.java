package com.gabriellorandi.paymentprocessing.operationtype.service;

import com.gabriellorandi.paymentprocessing.operationtype.domain.OperationType;
import com.gabriellorandi.paymentprocessing.operationtype.repository.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Slf4j
@EnableCaching
@Service
@RequiredArgsConstructor
public class OperationTypeCacheServiceImpl implements OperationTypeCacheService {

    private final OperationTypeRepository repository;

    @Override
    @Cacheable(cacheNames = "cacheStore", key = "#id")
    public OperationType findById(Integer id) {
        log.info("Returning OperationType");
        return repository.findById(id).orElse(null);
    }

}
