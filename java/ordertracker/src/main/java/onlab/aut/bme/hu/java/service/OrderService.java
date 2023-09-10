package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.domain.GetPaymentUrlRequest;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Delivery;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.repository.*;
import onlab.aut.bme.hu.java.validator.OrderValidator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderValidator orderValidator;
    private final RestTemplate restTemplate = new RestTemplate();

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
        orderValidator.validateOrderRequest(customerId, merchantId, order);
        if (order.getDelivery() == null) {
            Delivery delivery = new Delivery();
            delivery.setStatus("Ordered");
            delivery.setType("Delivery");
            order.setDelivery(delivery);
        }
        saveDeliveryAndShoppingCartAndConnectTheirData(customerId, order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    private void saveDeliveryAndShoppingCartAndConnectTheirData(Long customerId, Order order) {
        deliveryRepository.save(order.getDelivery());
        Order ord = orderRepository.save(order);
        customerRepository.findCustomerById(customerId).get().getShoppingCart().setOrderId(ord.getId());
        order.getDelivery().setOrder(order);
        shoppingCartRepository.save(customerRepository.findCustomerById(customerId).get().getShoppingCart());
        deliveryRepository.save(order.getDelivery());
    }

    public String getPaymentUrl(Long orderId) {
        Customer shoppingCart = customerRepository.findById(orderId).orElseThrow();
        GetPaymentUrlRequest paymentUrlRequest = GetPaymentUrlRequest.builder()
                .currency("USD")
                .products(shoppingCart.getShoppingCart().getProducts())
                .build();
        HttpEntity<GetPaymentUrlRequest> entity = new HttpEntity<>(paymentUrlRequest);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/product", HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
