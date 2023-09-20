package com.dg.spring6restmvclombok.repositories;

import com.dg.spring6restmvclombok.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder().name("test customer").build());

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("test customer");
    }
}