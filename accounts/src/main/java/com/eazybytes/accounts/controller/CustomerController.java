package com.eazybytes.accounts.controller;


import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for customer in SumanthGollaprolu-Bank",
        description = "CRUD REST APIs in SumanthGollaprolu-Bank to CREATE, UPDATE, FETCH AND DELETE  customer details"
)

@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RestController
public class CustomerController {

 private static   final Logger logger = LoggerFactory.getLogger(CustomerController.class) ;

  private final  ICustomersService iCustomersService ;

    public CustomerController(ICustomersService iCustomersService) {
        this.iCustomersService = iCustomersService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch complete  Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("sumanthbank-correlation-id") String correlationId,  @RequestParam  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must String mobileNumber")
                                                                         String mobileNumber)
   {
//        logger.debug("sumanthBank-correlation-id found : {} " + correlationId ) ;

       logger.debug(" fetchCustomerDetails method start") ;
       CustomerDetailsDto customerDetailsDto =  iCustomersService.fetchCustomerDetailsDto(mobileNumber , correlationId) ;

       logger.debug(" fetchCustomerDetails method end") ;

       return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto) ;

    }


}
