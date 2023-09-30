package onlab.aut.bme.hu.java.service;

import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Delivery;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import onlab.aut.bme.hu.java.repository.ShoppingCartRepository;
import onlab.aut.bme.hu.java.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DeliveryRepository deliveryRepository;
    @InjectMocks
    private ApiService service;

    @Test
    void findAllAddressesTest() {
        service.findAllAddresses();
    }

    @Test
    void getProductsTest() {
        service.getProducts();
    }

    @Test
    void getShoppingCartsTest() {
        service.getShoppingCarts();
    }

    @Test
    void getProductByIdTest() {
        Product product = new Product();
        when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        service.getProductById(1L);
        assertEquals(product,service.getProductById(1L).getBody());
        when(productRepository.findProductById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND,service.getProductById(1L).getStatusCode());
    }

    @Test
    void getShoppingCartTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartRepository.findById(any())).thenReturn(Optional.of(shoppingCart));
        service.getShoppingCart(1L);
        assertEquals(shoppingCart,service.getShoppingCart(1L).getBody());
        when(shoppingCartRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND,service.getShoppingCart(1L).getStatusCode());
    }

    @Test
    void saveShoppingCartTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProducts(new ArrayList<Product>());
        Customer customer  =new Customer();
        customer.setId(1L);
        shoppingCart.setCustomer(customer);
        service.saveShoppingCart(shoppingCart);
    }

    @Test
    void saveShoppingCartProductsTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(new Customer());
        shoppingCart.setId(1L);
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        shoppingCart.setProducts(products);
        ResponseEntity<ShoppingCart> expectedResponse = new ResponseEntity<>(shoppingCart, HttpStatus.OK);

        when(shoppingCartRepository.save(any())).thenReturn(shoppingCart);

        ResponseEntity<ShoppingCart> actualResponse = service.saveShoppingCart(shoppingCart);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void connectMerchantTest() {
        List<Product> products = new ArrayList<>();
        List<Merchant> merchants = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        products.add(product1);
        products.add(product2);
        Merchant merchant1 = new Merchant();
        Merchant merchant2 = new Merchant();
        merchants.add(merchant1);
        merchants.add(merchant2);
        when(productRepository.findAll()).thenReturn(products);
        when(merchantRepository.findAll()).thenReturn(merchants);
        service.connectMerchant();
        ArgumentCaptor<Merchant> merchantCaptor = ArgumentCaptor.forClass(Merchant.class);
        for (Product product : products) {
            if (product.getMerchant() == null) {
                Mockito.verify(product).setMerchant(merchantCaptor.capture());
            }
        }
        List<Merchant> capturedMerchants = merchantCaptor.getAllValues();
        for (Merchant merchant : capturedMerchants) {
            assertNotNull(merchant);
        }
    }

    @Test
    void patchOrderStatusTest() {
        Order order = new Order();
        order.setDelivery(new Delivery());
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        assertEquals(HttpStatus.OK, service.patchOrderStatus(1L, "delivery").getStatusCode());
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.patchOrderStatus(1L, "delivery").getStatusCode());
    }

    @Test
    void getShoppingCartProductsTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProducts(new ArrayList<>());
        when(shoppingCartRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getShoppingCartProducts(1L).getStatusCode());
        when(shoppingCartRepository.findById(any())).thenReturn(Optional.of(shoppingCart));
        assertEquals(HttpStatus.OK, service.getShoppingCartProducts(1L).getStatusCode());
    }

    @Test
    void getOrderDeliveryTest() {
        Order order = new Order();
        order.setDelivery(new Delivery());
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getOrderDelivery(1L).getStatusCode());
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        assertEquals(HttpStatus.OK, service.getOrderDelivery(1L).getStatusCode());
    }

}
