package com.dg.spring6restmvclombok.Services;

import com.dg.spring6restmvclombok.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImp implements CustomerService{

    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImp() {
        this.customerMap = new HashMap<>();

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .name("Bobby")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .name("Ellie")
                .id(UUID.randomUUID())
                .version(2)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .name("Damien")
                .id(UUID.randomUUID())
                .version(3)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getId(), customerDTO1);
        customerMap.put(customerDTO2.getId(), customerDTO2);
        customerMap.put(customerDTO3.getId(), customerDTO3);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> getCustomerList() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {

        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .name(customerDTO.getName())
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(newCustomerDTO.getId(), newCustomerDTO);

        return newCustomerDTO;
    }

    @Override
    public void updateExistingCustomer(UUID id, CustomerDTO customerDTO) {

        CustomerDTO existingCustomerDTO = customerMap.get(id);
        existingCustomerDTO.setName(customerDTO.getName());
        existingCustomerDTO.setVersion(1);
        existingCustomerDTO.setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomer(UUID customerID) {
        customerMap.remove(customerID);
    }

    @Override
    public void patchCustomerById(UUID customerID, CustomerDTO customerDTO) {
        CustomerDTO existingCustomerDTO = customerMap.get(customerID);

        if(StringUtils.hasText(customerDTO.getName())) {
            existingCustomerDTO.setName(customerDTO.getName());
            existingCustomerDTO.setLastModifiedDate(LocalDateTime.now());
        }


    }
}
