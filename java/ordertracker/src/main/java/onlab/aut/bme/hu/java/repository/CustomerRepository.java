package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;



public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findByUser_Email(@NonNull String email);

}
