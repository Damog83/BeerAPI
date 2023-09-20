package com.dg.spring6restmvclombok.controllers;

import com.dg.spring6restmvclombok.services.CustomerService;
import com.dg.spring6restmvclombok.model.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController

public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers(){
        log.debug("listCustomer method called in CustomerController");
        return customerService.getCustomerList();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("getCustomerById method called in CustomerController");
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<String> saveNewCustomer(@RequestBody CustomerDTO customerDTO){
        log.debug("saveNewCustomer method called in CustomerController");
        CustomerDTO savedCustomerDTO = customerService.saveNewCustomer(customerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomerDTO.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {
        log.debug("updateCustomerById method called in CustomerController");
        customerService.updateExistingCustomer(customerId, customerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") UUID customerId){
        log.debug("deleteCustomerById method called in CustomerController");
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> updateCustomerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {
        log.debug("updateCustomerPatchById method called in CustomerController");
        customerService.patchCustomerById(customerId, customerDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
