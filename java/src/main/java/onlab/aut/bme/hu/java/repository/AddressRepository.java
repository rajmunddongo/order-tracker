package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
