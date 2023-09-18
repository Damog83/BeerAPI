package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.CustomerService;
import com.dg.spring6restmvclombok.model.Customer;
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
    public List<Customer> listCustomers(){
        log.debug("listCustomer method called in CustomerController");
        return customerService.getCustomerList();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("getCustomerById method called in CustomerController");
        return customerService.getCustomerById(customerId);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<String> saveNewCustomer(@RequestBody Customer customer){
        log.debug("saveNewCustomer method called in CustomerController");
        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        log.debug("updateCustomerById method called in CustomerController");
        customerService.updateExistingCustomer(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") UUID customerId){
        log.debug("deleteCustomerById method called in CustomerController");
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> updateCustomerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        log.debug("updateCustomerPatchById method called in CustomerController");
        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
