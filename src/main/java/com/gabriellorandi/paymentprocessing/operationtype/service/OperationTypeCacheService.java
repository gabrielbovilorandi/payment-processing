package com.gabriellorandi.paymentprocessing.operationtype.service;

import com.gabriellorandi.paymentprocessing.operationtype.domain.OperationType;

public interface OperationTypeCacheService {

    OperationType findById(Integer id);

}
