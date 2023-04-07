package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.entity.Delivery;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    ApiService apiService;
    @Autowired
    MerchantRepository merchantRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public Product getProduct(Long id) {
        return productRepository.findProductById(id).orElseThrow();
    }

    public ResponseEntity saveOrder(Order order, Long customerId, Long merchantId){
        order.setOrderDate(LocalDateTime.now());
        if(customerRepository.findCustomerById(customerId).isPresent()){
            order.setCustomer(customerRepository.findCustomerById(customerId).get());
        }
        if(merchantRepository.findById(merchantId).isPresent()){
            order.setMerchant(merchantRepository.findById(merchantId).get());
        }
        if(order.getDelivery()== null){
            Delivery delivery = new Delivery();
            delivery.setStatus("Ordered");
            delivery.setType("Delivery");
            order.setDelivery(delivery);
        }
        deliveryRepository.save(order.getDelivery());
        orderRepository.save(order);
        order.getDelivery().setOrder(order);
        return new ResponseEntity(deliveryRepository.save(order.getDelivery()), HttpStatus.OK);
    }
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
