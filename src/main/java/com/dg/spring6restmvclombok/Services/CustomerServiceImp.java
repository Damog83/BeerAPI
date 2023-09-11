package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImp implements CustomerService{

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImp() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .name("Bobby")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .name("Ellie")
                .id(UUID.randomUUID())
                .version(2)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .name("Damien")
                .id(UUID.randomUUID())
                .version(3)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public List<Customer> getCustomerList() {
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {

        Customer newCustomer = Customer.builder()
                .name(customer.getName())
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(newCustomer.getId(), newCustomer);

        return newCustomer;
    }

    @Override
    public void updateExistingCustomer(UUID id, Customer customer) {

        Customer existingCustomer = customerMap.get(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setVersion(1);
        existingCustomer.setLastModifiedDate(LocalDateTime.now());

        customerMap.put(id,existingCustomer);
    }

}
