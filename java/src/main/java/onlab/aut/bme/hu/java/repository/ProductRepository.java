package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {
    public Optional<Product> findProductById(Long id);
}
