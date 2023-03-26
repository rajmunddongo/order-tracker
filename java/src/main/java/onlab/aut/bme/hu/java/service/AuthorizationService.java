package onlab.aut.bme.hu.java.service;

import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.*;
import onlab.aut.bme.hu.java.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AuthorizationService {

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

    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id).orElseThrow();
    }

    public void saveCustomer(Customer customer) {
        addressRepository.save(customer.getAddress());
        customer.setShoppingCart(new ShoppingCart());
        shoppingCartRepository.save(customer.getShoppingCart());
        customerRepository.save(customer);
    }

    public Address findCustomerAddressById(Long id) {
        Customer customer = customerRepository.findCustomerById(id).orElseThrow();
        return customer.getAddress();
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Merchant> findAllMerchants() {
        return merchantRepository.findAll();
    }

    public Merchant findMerchantById(Long id) {
        return merchantRepository.findById(id).orElseThrow();
    }

    public void saveMerchant(Merchant merchant) {
        addressRepository.save(merchant.getAddress());
        productRepository.saveAll(merchant.getProducts());
        merchantRepository.save(merchant);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity postProduct(Product product) {
        merchantRepository.save(product.getMerchant());
        orderRepository.saveAll(product.getOrder());
        deliveryRepository.save(product.getDelivery());
        shoppingCartRepository.save(product.getShoppingCart());
        return new ResponseEntity(productRepository.save(product), HttpStatus.OK);
    }

    public ResponseEntity getProductById(Long id) {
        if (productRepository.findProductById(id).isPresent()) {
            return new ResponseEntity(productRepository.findProductById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity saveShoppingCart(ShoppingCart shoppingCart) {
        customerRepository.save(shoppingCart.getCustomer());
        return new ResponseEntity(shoppingCartRepository.save(shoppingCart), HttpStatus.OK);
    }

    public ResponseEntity getShoppingCarts() {
        return new ResponseEntity(shoppingCartRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getShoppingCart(Long id) {
        if (shoppingCartRepository.findById(id).isPresent()) {
            return new ResponseEntity(shoppingCartRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
