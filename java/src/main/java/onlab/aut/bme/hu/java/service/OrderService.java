package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.Customer;
import onlab.aut.bme.hu.java.model.Delivery;
import onlab.aut.bme.hu.java.model.Order;
import onlab.aut.bme.hu.java.model.Product;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
            Customer customer = order.getCustomer();
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(order);
            customer.setOrders(orders);
            authorizationService.saveCustomer(order.getCustomer());
        }
        if(!deliveryRepository.findDeliveryById(order.getDelivery().getId()).isPresent()) {
            Delivery delivery = new Delivery();
            delivery.setOrder(order);
            deliveryRepository.save(order.getDelivery());
        }
        for(Product product : order.getProduct()){
            if(!productRepository.findProductById(product.getId()).isPresent()) {
                productRepository.save(product);
            }
        }
        orderRepository.save(order);
        for(Product product : order.getProduct()){
            List<Order> orders = product.getOrder();
            if(orders == null) {
                orders = new ArrayList<>();
            }
            orders.add(order);
            product.setOrder(orders);
            productRepository.save(product);
        }
    }
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
