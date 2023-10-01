package onlab.aut.bme.hu.java.service;

import onlab.aut.bme.hu.java.entity.Coupon;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Delivery;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    RestTemplate restTemplate;


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

    /*
    @Test
    void getPaymentUrlTest() throws URISyntaxException {
        service = new OrderService(customerRepository,orderRepository,deliveryRepository,shoppingCartRepository,orderValidator,couponRepository,restTemplate);
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        Customer customer = new Customer();
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = new Product();
        product.setPrice(1L);
        shoppingCart.setCouponPrecentage(BigDecimal.ONE);
        shoppingCart.setProducts(Collections.singletonList(product));
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        mockServer.expect(requestTo(new URI("http://localhost:8082/api/product")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("Mocked response body", MediaType.TEXT_PLAIN));
        String paymentUrl = service.getPaymentUrl(1L);
        assertEquals("Mocked response body", paymentUrl);
        mockServer.verify();
    }
    */





    @Test
    void calculateDiscountedPriceTest() {

        long originalPrice = 100L;
        BigDecimal discountPercentage = BigDecimal.valueOf(20);

        long discountedPrice = service.calculateDiscountedPrice(originalPrice, discountPercentage);

        assertEquals(80L, discountedPrice);
    }

    @Test
    void calculateDiscountedPriceThrowsTest() {
        long originalPrice = 100L;
        BigDecimal invalidDiscountPercentage = BigDecimal.valueOf(120);
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateDiscountedPrice(originalPrice, invalidDiscountPercentage);
        });
        assertEquals("Discount percentage must be between 0 and 100", thrownException.getMessage());
    }

    @Test
    void listOrdersTest() {
        service.listOrders();
        verify(orderRepository).findAll();
    }

    @Test
    void postCouponTest() {
        when(couponRepository.findCouponByCode(any())).thenReturn(Optional.empty());
        assertEquals(BigDecimal.ZERO, service.postCoupon("dwa",1L));
        Customer customer = new Customer();
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = new Product();
        product.setPrice(1L);
        shoppingCart.setCouponPrecentage(BigDecimal.ONE);
        customer.setShoppingCart(shoppingCart);
        Coupon coupon = new Coupon();
        coupon.setPrecentage(BigDecimal.ONE);
        coupon.setCode("ADW");
        when(couponRepository.findCouponByCode(any())).thenReturn(Optional.of(coupon));
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));
        assertEquals(BigDecimal.valueOf(2L), service.postCoupon("dwa",1L));
    }

    @Test
    void getPercantageTest() {
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.empty());
        assertEquals(BigDecimal.ZERO, service.getPrecentage(1L));
        Customer customer = new Customer();
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = new Product();
        product.setPrice(1L);
        shoppingCart.setCouponPrecentage(BigDecimal.ONE);
        customer.setShoppingCart(shoppingCart);
        when(customerRepository.findCustomerById(any())).thenReturn(Optional.of(customer));
        assertEquals(BigDecimal.ONE, service.getPrecentage(1L));
    }
}
