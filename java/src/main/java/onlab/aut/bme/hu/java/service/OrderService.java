package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Delivery;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final MerchantRepository merchantRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public ResponseEntity<Order> getOrder(Long id) {
        if (orderRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(orderRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Order> saveOrder(ArrayList<Product> products, Long customerId, Long merchantId) {
        Order order = new Order();
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        if (customerRepository.findCustomerById(customerId).isPresent()) {
            order.setCustomer(customerRepository.findCustomerById(customerId).get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (merchantRepository.findById(merchantId).isPresent()) {
            order.setMerchant(merchantRepository.findById(merchantId).get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (order.getDelivery() == null) {
            Delivery delivery = new Delivery();
            delivery.setStatus("Ordered");
            delivery.setType("Delivery");
            order.setDelivery(delivery);
        }
        deliveryRepository.save(order.getDelivery());
        Order ord = orderRepository.save(order);
        customerRepository.findCustomerById(customerId).get().getShoppingCart().setOrderId(ord.getId());
        order.getDelivery().setOrder(order);
        shoppingCartRepository.save(customerRepository.findCustomerById(customerId).get().getShoppingCart());
        deliveryRepository.save(order.getDelivery());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
