package com.payment.orchestration.service.payment_orchestration_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.payment.orchestration.service.payment_orchestration_service.entity.Customer;
import com.payment.orchestration.service.payment_orchestration_service.respository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getCustomerByCustomerId(String customerId) {
        return customerRepository.findById(customerId);
    }

    // public Customer saveCustomer(Customer customer) {
    //     return customerRepository.save(customer);
    // }

    // public boolean existsById(String customerId) {
    //     return customerRepository.existsById(customerId);
    // }
}
