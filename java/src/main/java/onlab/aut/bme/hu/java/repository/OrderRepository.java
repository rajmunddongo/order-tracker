package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
