package onlab.aut.bme.hu.java.service;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import onlab.aut.bme.hu.java.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    private CustomerRepository customerRepository;
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
    void getProductByIdTest() {
        Product product = new Product();
        when(productRepository.findProductById(Mockito.any())).thenReturn(Optional.of(product));
        service.getProductById(1L);
        assertEquals(product,service.getProductById(1L).getBody());
        when(productRepository.findProductById(Mockito.any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND,service.getProductById(1L).getStatusCode());

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
}
