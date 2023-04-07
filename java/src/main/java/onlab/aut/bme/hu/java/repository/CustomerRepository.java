package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerById(Long id);
    Optional<Customer> findCustomerByUsername(String username);
}
