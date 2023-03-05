package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.Product;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public Product getProduct(Long id) {
        return productRepository.findProductById(id).orElseThrow();
    }
}
