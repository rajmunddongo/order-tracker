package onlab.aut.bme.hu.java.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.*;
import onlab.aut.bme.hu.java.repository.*;
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


    CustomerRepository customerRepository;

    AddressRepository addressRepository;
    MerchantRepository merchantRepository;
    ProductRepository productRepository;
    ShoppingCartRepository shoppingCartRepository;
    OrderRepository orderRepository;
    DeliveryRepository deliveryRepository;


    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id).orElseThrow();
    }

    public void saveCustomer(Customer customer) {
        addressRepository.save(customer.getAddress());
        ShoppingCart shoppingCart = new ShoppingCart();
        customer.setShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        customerRepository.save(customer);
        shoppingCart.setCustomer(customer);
        shoppingCartRepository.save(shoppingCart);
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

    public ResponseEntity<Merchant> findMerchantById(Long id) {
        if (merchantRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(merchantRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Address> getMerchantAddress(Long id) {
        if (merchantRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(merchantRepository.findById(id).get().getAddress(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void saveMerchant(Merchant merchant) {
        addressRepository.save(merchant.getAddress());
        if (merchant.getProducts() != null)
            productRepository.saveAll(merchant.getProducts());
        merchantRepository.save(merchant);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity<Product> postProduct(Product product, Long id) {
        if (merchantRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.setMerchant(merchantRepository.findById(id).get());
            productRepository.save(product);
        }
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
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
        shoppingCartRepository.save(shoppingCart);
        shoppingCart.getCustomer().setShoppingCart(shoppingCart);
        return new ResponseEntity<>(shoppingCartRepository.save(shoppingCart), HttpStatus.OK);
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
                shoppingCart = new ShoppingCart();
                shoppingCart.setCustomer(customer);
                shoppingCart.setProducts(new ArrayList<>());
                shoppingCartRepository.save(shoppingCart);
                customer.setShoppingCart(shoppingCart);
                customerRepository.save(customer);
            }

            List<Product> products = shoppingCart.getProducts();
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
            shoppingCartRepository.save(shoppingCart);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    private boolean shoppingcartContainsProduct(Product product, ShoppingCart shoppingCart) {
        if (product.getId() == null) return false;
        if (shoppingCart.getProducts() != null)
            for (Product prod : shoppingCart.getProducts()) {
                if (product.getId().equals(prod.getId())) return true;
            }
        return false;
    }

    private void removeProductById(List<Product> products, Long id) {
        Product remove = null;
        for (Product product : products) {
            if (product.getId().equals(id)) {
                remove = product;
            }
        }
        products.remove(remove);
    }

    public ResponseEntity<List<Order>> getOrdersOfMerchant(Long id) {
        if (merchantRepository.findById(id).isPresent()) {
            Merchant merchant = merchantRepository.findById(id).get();
            return new ResponseEntity<>(merchant.getOrders(), HttpStatus.OK);
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

    public ResponseEntity<List<Product>> getMerchantProducts(Long id) {
        if (merchantRepository.findById(id).isPresent() && merchantRepository.findById(id).get().getProducts() != null) {
            Merchant merchant = merchantRepository.findById(id).get();
            return new ResponseEntity<>(merchant.getProducts(), HttpStatus.OK);
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

    public ResponseEntity<Merchant> getMerchantFromProductId(Long id) {
        if (productRepository.findProductById(id).isPresent() && productRepository.findProductById(id).get().getMerchant() != null) {
            return new ResponseEntity<>(productRepository.findProductById(id).get().getMerchant(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}