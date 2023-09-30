package onlab.aut.bme.hu.java.service;

import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Delivery;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.repository.CouponRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ShoppingCartRepository;
import onlab.aut.bme.hu.java.validator.OrderValidator;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @InjectMocks
    OrderService service;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    DeliveryRepository deliveryRepository;
    @Mock
    ShoppingCartRepository shoppingCartRepository;
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    OrderValidator orderValidator;
    @Mock
    CouponRepository couponRepository;
    @Mock
    RestTemplate restTemplate = new RestTemplate();

    @Test
    void getOrderTest() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getOrder(1L).getStatusCode());
        when(orderRepository.findById(any())).thenReturn(Optional.of(new Order()));
        assertEquals(HttpStatus.OK, service.getOrder(1L).getStatusCode());
    }

    @Test
    void saveOrderTest() {
        Customer customer = new Customer();
        customer.setShoppingCart(new ShoppingCart());
        Order order = new Order();
        order.setDelivery(new Delivery());
        when(orderRepository.save(any())).thenReturn(order);
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));
        service.saveOrder(new ArrayList<Product>(Collections.singletonList(new Product())), 1L,1L);
        verify(deliveryRepository,times(2)).save(any());
    }
}
