package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    public Optional<Delivery> findDeliveryById(Long id);
}
