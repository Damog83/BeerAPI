package com.dg.spring6restmvclombok.bootstrap;

import com.dg.spring6restmvclombok.repositories.BeerRepository;
import com.dg.spring6restmvclombok.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BootstrapTestDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapTestData bootstrapTestData;

    @BeforeEach
    void setUp() {
        bootstrapTestData = new BootstrapTestData(beerRepository, customerRepository);
    }

    @Test
    void testDataLoadingInBootStrap() {
        bootstrapTestData.run(null);
        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}