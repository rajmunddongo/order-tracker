package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
