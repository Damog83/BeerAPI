package com.dg.spring6restmvclombok.services;

import com.dg.spring6restmvclombok.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getCustomerById(UUID id);

    List<CustomerDTO> getCustomerList();

    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);

    void updateExistingCustomer(UUID id, CustomerDTO customerDTO);

    void deleteCustomer(UUID customerID);

    void patchCustomerById(UUID customerID, CustomerDTO customerDTO);

}
