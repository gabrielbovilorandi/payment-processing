package com.gabriellorandi.paymentprocessing.account.application;

import com.gabriellorandi.paymentprocessing.account.application.dto.AccountResponse;
import com.gabriellorandi.paymentprocessing.account.application.dto.CreateAccountRequest;
import com.gabriellorandi.paymentprocessing.account.domain.Account;
import com.gabriellorandi.paymentprocessing.account.exception.AccountAlreadyExistException;
import com.gabriellorandi.paymentprocessing.account.exception.AccountNotFoundException;
import com.gabriellorandi.paymentprocessing.account.repository.AccountRepository;
import com.gabriellorandi.paymentprocessing.common.application.annotation.ApiPageable;
import com.gabriellorandi.paymentprocessing.common.application.response.ApiCollectionResponse;
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
@RequestMapping(path = "/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Account Operations")
public class AccountController {

    private final AccountRepository repository;

    @GetMapping
    @ApiPageable
    @ApiOperation("Retrieve all accounts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation")
    })
    public ResponseEntity<ApiCollectionResponse<AccountResponse>> findAll(@ApiIgnore Pageable pageable) {
        log.info("Receiving request to find all accounts.");

        Page<AccountResponse> accounts = repository.findAll(pageable)
                .map(AccountResponse::from);

        ApiCollectionResponse<AccountResponse> response = ApiCollectionResponse.from(accounts);

        log.info("Retrieving all accounts.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve an account by given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 404, message = "Account not found")
    })
    public ResponseEntity<AccountResponse> findById(@PathVariable String id) {
        log.info("Receiving request to find an account by id {}.", id);

        AccountResponse response = repository.findById(UUID.fromString(id))
                .map(AccountResponse::from)
                .orElseThrow(() -> new AccountNotFoundException(UUID.fromString(id)));

        log.info("Retrieving account id {}.", id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Create new account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 500, message = "Document name already in use")
    })
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid CreateAccountRequest request) {
        log.info("Receiving request to create new account with Document name: {}.", request.getDocumentNumber());

        if (repository.existsByDocumentNumber(request.getDocumentNumber())) {
            log.error("Error creating account. Document name {} is already in use.", request.getDocumentNumber());
            throw new AccountAlreadyExistException(request.getDocumentNumber());
        }

        Account newAccount = Account.from(request);
        repository.save(newAccount);

        log.info("Account created. Id: {}.", newAccount.getId());

        AccountResponse response = AccountResponse.from(newAccount);
        return ResponseEntity.ok(response);
    }

}
