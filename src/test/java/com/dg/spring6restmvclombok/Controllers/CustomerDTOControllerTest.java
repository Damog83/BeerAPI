package com.dg.spring6restmvclombok.Controllers;

import com.dg.spring6restmvclombok.Services.CustomerService;
import com.dg.spring6restmvclombok.Services.CustomerServiceImp;
import com.dg.spring6restmvclombok.model.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerDTOControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImp customerServiceImp;

    @BeforeEach
    void setUp() {
        customerServiceImp = new CustomerServiceImp();
    }
    @Test
    void getCustomerByIdNotFound() throws Exception {
        UUID testCustomerId = UUID.randomUUID();

        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomerId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImp.getCustomerList().get(0);

        given(customerService.getCustomerById(testCustomerDTO.getId())).willReturn(Optional.of(testCustomerDTO));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomerDTO.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomerDTO.getName())))
                .andExpect(jsonPath("$.version", is(testCustomerDTO.getVersion())));

        verify(customerService).getCustomerById(uuidArgumentCaptor.capture());

        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getCustomerList() throws Exception {
        given(customerService.getCustomerList()).willReturn(customerServiceImp.getCustomerList());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void saveNewCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImp.getCustomerList().get(0);
        testCustomerDTO.setVersion(0);
        testCustomerDTO.setId(null);

        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(customerServiceImp.getCustomerList().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCustomerByID() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImp.getCustomerList().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateExistingCustomer(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(testCustomerDTO.getName()).isEqualTo(customerArgumentCaptor.getValue().getName());
    }

    @Test
    void deleteCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImp.getCustomerList().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(uuidArgumentCaptor.capture());

        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void updateCustomerPatchById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImp.getCustomerList().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "New Customer Name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(customerMap.get("name")).isEqualTo(customerArgumentCaptor.getValue().getName());
    }
}