package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.Order;
import onlab.aut.bme.hu.java.model.Product;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    AuthorizationService authorizationService;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public Product getProduct(Long id) {
        return productRepository.findProductById(id).orElseThrow();
    }

    public  void saveOrder(Order order){
        if(!customerRepository.findCustomerById(order.getCustomer().getId()).isPresent()) {
            authorizationService.saveCustomer(order.getCustomer());
        }
        if(!deliveryRepository.findDeliveryById(order.getDelivery().getId()).isPresent()) {
            deliveryRepository.save(order.getDelivery());
        }
            productRepository.saveAll(order.getProduct());

        orderRepository.save(order);
    }
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
