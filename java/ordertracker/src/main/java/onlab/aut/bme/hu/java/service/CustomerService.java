package onlab.aut.bme.hu.java.service;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.*;
import onlab.aut.bme.hu.java.provider.ShoppingCartProvider;
import onlab.aut.bme.hu.java.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static onlab.aut.bme.hu.java.utils.CustomerUtils.removeProductById;
import static onlab.aut.bme.hu.java.utils.CustomerUtils.shoppingcartContainsProduct;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShoppingCartProvider shoppingCartProvider;

    public void saveCustomer(Customer customer) {
        addressRepository.save(customer.getAddress());
        ShoppingCart shoppingCart = new ShoppingCart();
        customer.setShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        customerRepository.save(customer);
        shoppingCart.setCustomer(customer);
        shoppingCartRepository.save(shoppingCart);
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id).orElseThrow();
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Address findCustomerAddressById(Long id) {
        Customer customer = customerRepository.findCustomerById(id).orElseThrow();
        return customer.getAddress();
    }

    public ResponseEntity<List<Product>> getCustomerShoppingCartProducts(Long customerId) {
        if (customerRepository.findCustomerById(customerId).isPresent()) {
            Customer customer = customerRepository.findCustomerById(customerId).get();
            if (!customer.getShoppingCart().getProducts().isEmpty()) {
                return new ResponseEntity<>(customer.getShoppingCart().getProducts(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> addToCustomerShoppingCartProduct(Product product, Long customerId) {
        if (customerRepository.findCustomerById(customerId).isPresent() && productRepository.findProductById(product.getId()).isPresent()) {
            Customer customer = customerRepository.findCustomerById(customerId).get();
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if (shoppingCart == null) {
                shoppingCart = shoppingCartProvider.provide(customer);
                saveCustomerShoppingCart(shoppingCart, customer);
            }
            addShoppingCartToProductShoppingCarts(product, shoppingCart);
            shoppingCartRepository.save(shoppingCart);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void addShoppingCartToProductShoppingCarts(Product product, ShoppingCart shoppingCart) {
        List<Product> products = shoppingCart.getProducts();
        if(products == null) {
            products = new ArrayList<>();
        }
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        if (product.getShoppingCarts() != null) {
            shoppingCarts = product.getShoppingCarts();
        }
        shoppingCarts.add(shoppingCart);
        product.setShoppingCarts(shoppingCarts);
        productRepository.save(product);
        if (!shoppingcartContainsProduct(product, shoppingCart)) {
            products.add(product);
        }
        shoppingCart.setProducts(products);
    }

    public void saveCustomerShoppingCart(ShoppingCart shoppingCart, Customer customer) {
        shoppingCartRepository.save(shoppingCart);
        customer.setShoppingCart(shoppingCart);
        customerRepository.save(customer);
    }

    public ResponseEntity<ShoppingCart> deleteProductFromCart(Long prodId, Long customerId) {
        if (customerRepository.findCustomerById(customerId).isPresent()) {
            Customer customer = customerRepository.findCustomerById(customerId).get();
            ShoppingCart shoppingCart = customer.getShoppingCart();
            List<Product> products = shoppingCart.getProducts();
            removeProductById(products, prodId);
            shoppingCart.setProducts(products);
            return new ResponseEntity<>(shoppingCartRepository.save(shoppingCart), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Customer> getOrderCustomer(Long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            Order order = orderRepository.findById(orderId).get();
            if (order.getCustomer() != null) {
                return new ResponseEntity<>(order.getCustomer(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Long> getCustomerShoppingCartOrderId(Long id) {
        if (customerRepository.findById(id).isPresent() && customerRepository.findById(id).get().getShoppingCart() != null) {
            ShoppingCart shoppingCart = customerRepository.findById(id).get().getShoppingCart();
            if (shoppingCart.getOrderId() == null) {
                throw new IllegalArgumentException("OrderId is null");
            }
            return new ResponseEntity<>(shoppingCart.getOrderId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Long>  getPreviousOrderNumber (Long id) {
        if (customerRepository.findById(id).isPresent() && customerRepository.findById(id).get().getOrders() != null) {
            return new ResponseEntity<>((long) customerRepository.findById(id).get().getOrders().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
