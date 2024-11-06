package com.pttk.config;

import com.pttk.model.entity.Customer;
import com.pttk.model.entity.Role;
import com.pttk.repository.CustomerRepository;
import com.pttk.repository.RoleRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner, DisposableBean {

    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        setUpRoles();
        setUpCustomers();
    }

    private void setUpCustomers() {
        if (customerRepository.count() == 0) {
            customerRepository.saveAll(List.of(
                    Customer.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("123"))
                            .roles(roleRepository.findAll())
                            .build(),
                    Customer.builder()
                            .username("user")
                            .password(passwordEncoder.encode("123"))
                            .roles(List.of(roleRepository.findByName("USER")))
                            .build(),
                    Customer.builder()
                            .username("customer")
                            .password(passwordEncoder.encode("123"))
                            .roles(List.of(roleRepository.findByName("CUSTOMER")))
                            .build()
            ));
        }
    }

    private void setUpRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                    Role.builder().name("USER").build(),
                    Role.builder().name("ADMIN").build(),
                    Role.builder().name("CUSTOMER").build()
            ));
        }
    }

    @PreDestroy
    @Override
    public void destroy() throws Exception {
        clearData();
    }

    private void clearData() {
        customerRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
