package com.gabriellorandi.paymentprocessing.transaction.application;

import com.gabriellorandi.paymentprocessing.account.repository.AccountRepository;
import com.gabriellorandi.paymentprocessing.common.application.annotation.ApiPageable;
import com.gabriellorandi.paymentprocessing.common.application.response.ApiCollectionResponse;
import com.gabriellorandi.paymentprocessing.operationtype.repository.OperationTypeRepository;
import com.gabriellorandi.paymentprocessing.transaction.application.dto.CreateTransactionRequest;
import com.gabriellorandi.paymentprocessing.transaction.application.dto.TransactionResponse;
import com.gabriellorandi.paymentprocessing.transaction.domain.Transaction;
import com.gabriellorandi.paymentprocessing.transaction.exception.TransactionNotFoundException;
import com.gabriellorandi.paymentprocessing.transaction.repository.TransactionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Transaction Operations")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    @GetMapping
    @ApiPageable
    @ApiOperation("Retrieve all transactions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation")
    })
    public ResponseEntity<ApiCollectionResponse<TransactionResponse>> findAll(@ApiIgnore Pageable pageable) {
        log.info("Receiving request to find all transactions.");

        Page<TransactionResponse> transactions = transactionRepository.findAll(pageable)
                .map(TransactionResponse::from);

        ApiCollectionResponse<TransactionResponse> response = ApiCollectionResponse.from(transactions);

        log.info("Retrieving all transactions.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve a transaction by given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 404, message = "Transaction not found")
    })
    public ResponseEntity<TransactionResponse> findById(@PathVariable String id) {
        log.info("Receiving request to find a transaction by id {}.", id);

        TransactionResponse response = transactionRepository.findById(UUID.fromString(id))
                .map(TransactionResponse::from)
                .orElseThrow(() -> new TransactionNotFoundException(UUID.fromString(id)));

        log.info("Retrieving transaction id {}.", id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Create new transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 400, message = "Invalid Request")
    })
    public ResponseEntity<TransactionResponse> create(@RequestBody @Valid CreateTransactionRequest request) {
        log.info("Receiving request to create new transaction.");

        Transaction newTransaction = Transaction.from(request, accountRepository, operationTypeRepository);
        transactionRepository.save(newTransaction);

        log.info("Transaction created. Id: {}.", newTransaction.getId());

        TransactionResponse response = TransactionResponse.from(newTransaction);
        return ResponseEntity.ok(response);
    }

}
