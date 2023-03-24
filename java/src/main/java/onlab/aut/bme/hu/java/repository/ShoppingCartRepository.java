package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    public Optional<ShoppingCart> findById(Long id);
}
