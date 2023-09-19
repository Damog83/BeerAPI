package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<Customer> getCustomerById(UUID id);

    List<Customer> getCustomerList();

    Customer saveNewCustomer(Customer customer);

    void updateExistingCustomer(UUID id,Customer customer);

    void deleteCustomer(UUID customerID);

    void patchCustomerById(UUID customerID, Customer customer);

}
