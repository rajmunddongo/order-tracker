package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerById(Long id);
}
