package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.CustomerService;
import com.dg.spring6restmvclombok.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping()
    public List<Customer> listCustomers(){return customerService.getCustomerList();}

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping()
    public ResponseEntity<String> handlePost(@RequestBody Customer customer){

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + savedCustomer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerID}")
    public ResponseEntity<String> updateCustomerByID(@PathVariable("customerID") UUID customerID, @RequestBody Customer customer) {
        customerService.updateExistingCustomer(customerID, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{customerID}")
    public ResponseEntity<String> deleteCustomerByID(@PathVariable("customerID") UUID customerID){
        customerService.deleteCustomer(customerID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
