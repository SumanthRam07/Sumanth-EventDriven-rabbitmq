package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.*;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.Client.CardsFeignClient;
import com.eazybytes.accounts.service.Client.LoansFeignClient;
import com.eazybytes.accounts.service.ICustomersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {


    AccountsRepository accountsRepository ;
    CustomerRepository customerRepository ;

    private CardsFeignClient cardsFeignClient ;
    private LoansFeignClient loansFeignClient ;


    @Override
    public CustomerDetailsDto fetchCustomerDetailsDto(String mobileNumber ,String  correlationId) {


        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

       CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer , new CustomerDetailsDto()) ;



        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts , new AccountsDto()));

        ResponseEntity<LoansDto> loansDto =  loansFeignClient.fetchLoanDetails( correlationId , mobileNumber) ;


        if( null != loansDto)
        {


            customerDetailsDto.setLoansDto(loansDto.getBody());

        }
        ResponseEntity<CardsDto> cardsDto =  cardsFeignClient.fetchCardDetails( correlationId , mobileNumber ) ;

        if( null != cardsDto)
        {


            customerDetailsDto.setCardsDto(cardsDto.getBody());

        }



        return customerDetailsDto ;
    }
}
