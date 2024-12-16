package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {




CustomerDetailsDto fetchCustomerDetailsDto(String mobileNumber , String correlationId) ;



}
