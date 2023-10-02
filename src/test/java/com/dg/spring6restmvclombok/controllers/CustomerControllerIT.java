package com.dg.spring6restmvclombok.controllers;

import com.dg.spring6restmvclombok.entities.Customer;
import com.dg.spring6restmvclombok.model.CustomerDTO;
import com.dg.spring6restmvclombok.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void listCustomers() {
        List<CustomerDTO> customers = customerController.listCustomers();

        assertThat(customers.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyCustomerList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.listCustomers();

        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    void getCustomerById() {
        Customer testCustomer = customerRepository.findAll().get(0);
        CustomerDTO customer = customerController.getCustomerById(testCustomer.getId());

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(testCustomer.getId());
    }
    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
}