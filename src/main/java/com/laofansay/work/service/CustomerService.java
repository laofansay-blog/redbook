package com.laofansay.work.service;

import com.laofansay.work.domain.Customer;
import com.laofansay.work.repository.CustomerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.laofansay.work.domain.Customer}.
 */
@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Update a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    public Customer update(Customer customer) {
        log.debug("Request to update Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Partially update a customer.
     *
     * @param customer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getName() != null) {
                    existingCustomer.setName(customer.getName());
                }
                if (customer.getMobile() != null) {
                    existingCustomer.setMobile(customer.getMobile());
                }
                if (customer.getEmail() != null) {
                    existingCustomer.setEmail(customer.getEmail());
                }
                if (customer.getIntroduce() != null) {
                    existingCustomer.setIntroduce(customer.getIntroduce());
                }
                if (customer.getBalance() != null) {
                    existingCustomer.setBalance(customer.getBalance());
                }
                if (customer.getTimes() != null) {
                    existingCustomer.setTimes(customer.getTimes());
                }
                if (customer.getStatus() != null) {
                    existingCustomer.setStatus(customer.getStatus());
                }
                if (customer.getCreatedDate() != null) {
                    existingCustomer.setCreatedDate(customer.getCreatedDate());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Customer> findOne(String id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
