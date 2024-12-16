package com.eazybytes.accounts.service.Client;


import com.eazybytes.accounts.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans" , fallback = LoansFallBack.class)
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch" , consumes = "application/jason")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("sumanthbank-correlation-id") String correlationId  ,  @RequestParam  String mobileNumber)  ;



}



