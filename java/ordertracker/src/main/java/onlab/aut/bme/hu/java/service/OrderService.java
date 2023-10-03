package onlab.aut.bme.hu.java.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.domain.GetPaymentUrlRequest;
import onlab.aut.bme.hu.java.entity.Coupon;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderValidator orderValidator;
    private final CouponRepository couponRepository;
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

    public String getPaymentUrl(Long customerId) {
        GetPaymentUrlRequest paymentUrlRequest = GetPaymentUrlRequest.builder()
                .currency("USD")
                .products(getDiscountedProducts(customerId))
                .build();
        HttpEntity<GetPaymentUrlRequest> entity = new HttpEntity<>(paymentUrlRequest);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/product", HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    private List<Product> getDiscountedProducts(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        List<Product> products = customer.getShoppingCart().getProducts();
        for(Product product : products) {
            product.setPrice(calculateDiscountedPrice(product.getPrice(),customer.getShoppingCart().getCouponPrecentage()));
        }
        return products;
    }

    public long calculateDiscountedPrice(long originalPrice, BigDecimal discountPercentage) {
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }

        BigDecimal percentage = BigDecimal.valueOf(100).subtract(discountPercentage);
        BigDecimal discountedPrice = BigDecimal.valueOf(originalPrice).multiply(percentage).divide(BigDecimal.valueOf(100));
        return discountedPrice.longValue();
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public BigDecimal postCoupon(String code, Long id) {
        if (couponRepository.findCouponByCode(code).isEmpty()) return BigDecimal.ZERO;
        BigDecimal precentage = BigDecimal.ZERO;
        Coupon coupon = couponRepository.findCouponByCode(code).get();
        if (customerRepository.findCustomerById(id).isPresent() && customerRepository.findCustomerById(id).get().getShoppingCart() != null) {
            ShoppingCart shoppingCart = customerRepository.findCustomerById(id).get().getShoppingCart();
            if(!shoppingCart.getUsedCoupons().contains(code)) {
                precentage = shoppingCart.getCouponPrecentage();
                shoppingCart.setCouponPrecentage(calculateDiscountPercentage(precentage, coupon.getPrecentage()));
                List<String> coupons = shoppingCart.getUsedCoupons();
                coupons.add(code);
                shoppingCart.setUsedCoupons(coupons);
                shoppingCartRepository.save(shoppingCart);
            }
        }
        return calculateDiscountPercentage(precentage,coupon.getPrecentage());
    }

    private BigDecimal calculateDiscountPercentage(BigDecimal percentage, BigDecimal couponPercentage) {
        BigDecimal totalPercentage = percentage.add(couponPercentage);
        return totalPercentage.compareTo(BigDecimal.valueOf(90)) > 0 ? BigDecimal.valueOf(90) : totalPercentage;
    }

    public BigDecimal getPrecentage(Long id) {
        if (customerRepository.findCustomerById(id).isPresent() && customerRepository.findCustomerById(id).get().getShoppingCart() != null) {
            ShoppingCart shoppingCart = customerRepository.findCustomerById(id).get().getShoppingCart();
            return shoppingCart.getCouponPrecentage();
        }
        return BigDecimal.ZERO;
    }
}
