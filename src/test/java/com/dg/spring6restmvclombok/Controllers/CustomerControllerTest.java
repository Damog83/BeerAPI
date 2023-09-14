package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.CustomerService;
import com.dg.spring6restmvclombok.Services.CustomerServiceImp;
import com.dg.spring6restmvclombok.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImp customerServiceImp;

    @BeforeEach
    void setUp() {
        customerServiceImp = new CustomerServiceImp();
    }

    @Test
    void getCustomerById() throws Exception {
        Customer testCustomer = customerServiceImp.getCustomerList().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        mockMvc.perform(get("/api/v1/customers/" + testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));
    }
    @Test
    void getCustomerList() throws Exception {
        given(customerService.getCustomerList()).willReturn(customerServiceImp.getCustomerList());

        mockMvc.perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void saveNewCustomer() throws Exception {
        Customer testCustomer = customerServiceImp.getCustomerList().get(0);
        testCustomer.setVersion(0);
        testCustomer.setId(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImp.getCustomerList().get(1));

        mockMvc.perform(post("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCustomerByID() throws Exception {
        Customer testCustomer = customerServiceImp.getCustomerList().get(0);

        mockMvc.perform(put("/api/v1/customers/" + testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateExistingCustomer(any(UUID.class), any(Customer.class));
    }
}