package com.dg.spring6restmvclombok.mappers;

import com.dg.spring6restmvclombok.entities.Customer;
import com.dg.spring6restmvclombok.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
