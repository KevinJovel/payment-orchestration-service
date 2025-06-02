package com.payment.orchestration.service.payment_orchestration_service.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.orchestration.service.payment_orchestration_service.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> getCustomerByCustomerId(String customerId);

    Customer save(Customer customer);

    // boolean existsById(String customerId);
}
