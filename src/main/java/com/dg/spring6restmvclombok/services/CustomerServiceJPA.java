package com.dg.spring6restmvclombok.services;

import com.dg.spring6restmvclombok.mappers.CustomerMapper;
import com.dg.spring6restmvclombok.model.CustomerDTO;
import com.dg.spring6restmvclombok.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements  CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getCustomerList() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void updateExistingCustomer(UUID id, CustomerDTO customerDTO) {

    }

    @Override
    public void deleteCustomer(UUID customerID) {

    }

    @Override
    public void patchCustomerById(UUID customerID, CustomerDTO customerDTO) {

    }
}
