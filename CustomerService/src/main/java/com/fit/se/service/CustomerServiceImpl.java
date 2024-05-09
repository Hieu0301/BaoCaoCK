package com.fit.se.service;

import com.fit.se.entity.Clothing;
import com.fit.se.entity.Customer;
import com.fit.se.entity.Order;
import com.fit.se.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private RestTemplate restTemplate;

    @Override
    public Customer saveCustomer(Customer customer) {
        ResponseEntity<Order> responseEntity = restTemplate
                .getForEntity("http://localhost:8084/orders/" + customer.getOrder().getId(),
                        Order.class);
        Order order = responseEntity.getBody();
        customer.setOrder(order);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void deleteCustomerById(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer updateCustomerById(int id, Customer newCustomer) {
        Customer tempCustomer = customerRepository.findById(id).get();
        tempCustomer.setFirstName(newCustomer.getFirstName());
        tempCustomer.setLastName(newCustomer.getLastName());
        tempCustomer.setEmail(newCustomer.getEmail());
        return customerRepository.save(tempCustomer);
    }
}
