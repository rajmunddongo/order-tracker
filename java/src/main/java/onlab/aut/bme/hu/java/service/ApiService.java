package onlab.aut.bme.hu.java.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.*;
import onlab.aut.bme.hu.java.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DeliveryRepository deliveryRepository;



    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public ResponseEntity<Product> getProductById(Long id) {
        if (productRepository.findProductById(id).isPresent()) {
            return new ResponseEntity<>(productRepository.findProductById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ShoppingCart> saveShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.getCustomer().setShoppingCart(shoppingCart);
        customerRepository.save(shoppingCart.getCustomer());
        saveShoppingCartProducts(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        shoppingCart.getCustomer().setShoppingCart(shoppingCart);
        return new ResponseEntity<>(shoppingCartRepository.save(shoppingCart), HttpStatus.OK);
    }

    private void saveShoppingCartProducts(ShoppingCart shoppingCart) {
        for (Product product : shoppingCart.getProducts()) {
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            if (product.getShoppingCarts() != null) {
                shoppingCarts = product.getShoppingCarts();
            }
            shoppingCarts.add(shoppingCart);
            product.setShoppingCarts(shoppingCarts);
            productRepository.save(product);
            deliveryRepository.save(product.getDelivery());
        }
    }

    public ResponseEntity<List<ShoppingCart>> getShoppingCarts() {
        return new ResponseEntity<>(shoppingCartRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<ShoppingCart> getShoppingCart(Long id) {
        if (shoppingCartRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(shoppingCartRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void connectMerchant() {
        List<Product> products = productRepository.findAll();
        List<Merchant> merchants = merchantRepository.findAll();
        Random rand = new Random();
        for (Product product : products) {
            if (product.getMerchant() == null) {
                product.setMerchant(merchants.get(rand.nextInt(merchants.size())));
            }
        }
    }

    public ResponseEntity<Order> patchOrderStatus(Long orderId, String status) {
        if (orderRepository.findById(orderId).isPresent() && orderRepository.findById(orderId).get().getDelivery() != null) {
            Order order = orderRepository.findById(orderId).get();
            order.getDelivery().setStatus(status);
            deliveryRepository.save(order.getDelivery());
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Product>> getShoppingCartProducts(Long id) {
        if (shoppingCartRepository.findById(id).isPresent() && shoppingCartRepository.findById(id).get().getProducts() != null) {
            ShoppingCart shoppingCart = shoppingCartRepository.findById(id).get();
            return new ResponseEntity<>(shoppingCart.getProducts(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Delivery> getOrderDelivery(Long id) {
        if (orderRepository.findById(id).isPresent() && orderRepository.findById(id).get().getDelivery() != null) {
            return new ResponseEntity<>(orderRepository.findById(id).get().getDelivery(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}