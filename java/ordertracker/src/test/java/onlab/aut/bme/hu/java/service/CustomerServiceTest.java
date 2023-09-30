package onlab.aut.bme.hu.java.service;

import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.provider.ShoppingCartProvider;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import onlab.aut.bme.hu.java.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService service;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    AddressRepository addressRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ShoppingCartRepository shoppingCartRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ShoppingCartProvider shoppingCartProvider;

    @Test
    void saveCustomerTest() {
        Customer customer = new Customer();
        service.saveCustomer(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void findCustomerByIdTest() {
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(new Customer()));
        service.findCustomerById(1L);
        verify(customerRepository).findCustomerById(1L);
    }

    @Test
    void findAllCustomersTest() {
        service.findAllCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void findCustomerAddressByIdTest() {
        when(customerRepository.findCustomerById(1L)).thenReturn(Optional.of(new Customer()));
        assertNull(service.findCustomerAddressById(1L));
    }

    @Test
    void getCustomerShoppingCartProducts() {
        Customer customer = new Customer();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProducts(new ArrayList<>());
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findCustomerById(1L)).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.NOT_FOUND,service.getCustomerShoppingCartProducts(1L).getStatusCode());
        when(customerRepository.findCustomerById(1L)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND,service.getCustomerShoppingCartProducts(1L).getStatusCode());
        shoppingCart.setProducts(Collections.singletonList(new Product()));
        when(customerRepository.findCustomerById(1L)).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.OK,service.getCustomerShoppingCartProducts(1L).getStatusCode());
    }
    @Test
    void addToCustomerShoppingCartProductTest() {
        Customer customer = new Customer();
        Product product = new Product();
        product.setId(1L);
        when(shoppingCartProvider.provide(any())).thenReturn(new ShoppingCart());
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));
        when(customerRepository.findCustomerById(1L)).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.OK, service.addToCustomerShoppingCartProduct(product, 1L).getStatusCode());
        when(productRepository.findProductById(1L)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.addToCustomerShoppingCartProduct(product, 1L).getStatusCode());
        ShoppingCart shoppingCart = new ShoppingCart();
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findCustomerById(1L)).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.NOT_FOUND, service.addToCustomerShoppingCartProduct(product, 1L).getStatusCode());
    }
    @Test
    void saveCustomerShoppingCartTest() {
        service.saveCustomerShoppingCart(new ShoppingCart(),new Customer());
        verify(customerRepository).save(any());
    }

    @Test
    void deleteProductFromCart() {
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.deleteProductFromCart(1L,1L).getStatusCode());
        Customer customer = new Customer();
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = new Product();
        product.setId(2L);
        shoppingCart.setProducts(Collections.singletonList(product));
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.OK, service.deleteProductFromCart(1L,1L).getStatusCode());

    }

    @Test
    void getOrderCustomerTest() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getOrderCustomer(1L).getStatusCode());
        when(orderRepository.findById(any())).thenReturn(Optional.of(new Order()));
        assertEquals(HttpStatus.NOT_FOUND, service.getOrderCustomer(1L).getStatusCode());
        Order order = new Order();
        order.setCustomer(new Customer());
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        assertEquals(HttpStatus.OK, service.getOrderCustomer(1L).getStatusCode());
    }

    @Test
    void getCustomerShoppingCartOrderIdTest() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getCustomerShoppingCartOrderId(1L).getStatusCode());
        Customer customer = new Customer();
        ShoppingCart shoppingCart = new ShoppingCart();
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        assertThrows(IllegalArgumentException.class, () -> {
            service.getCustomerShoppingCartOrderId(1L);
        });
        shoppingCart.setOrderId(1L);
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.OK, service.getCustomerShoppingCartOrderId(1L).getStatusCode());
    }

    @Test
    void getPreviousOrderNumberTest() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getPreviousOrderNumber(1L).getStatusCode());
        Customer customer = new Customer();
        customer.setOrders(Collections.singletonList(new Order()));
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        assertEquals(HttpStatus.OK, service.getPreviousOrderNumber(1L).getStatusCode());
    }


}
