package onlab.aut.bme.hu.java.service;

import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class MerchantServiceTests {

    @InjectMocks
    MerchantService service;
    @Mock
    AddressRepository addressRepository;
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    ProductRepository productRepository;

    @Test
    void postProductTest() {
        when(merchantRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.postProduct(new Product(), 1L).getStatusCode());
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant()));
        assertEquals(HttpStatus.OK, service.postProduct(new Product(), 1L).getStatusCode());
    }

    @Test
    void findMerchantByIdTest() {
        when(merchantRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.findMerchantById(1L).getStatusCode());
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant()));
        assertEquals(HttpStatus.OK, service.findMerchantById(1L).getStatusCode());
    }

    @Test
    void saveMerchantTest() {
        Merchant merchant=  new Merchant();
        merchant.setProducts(Collections.singletonList(new Product()));
        service.saveMerchant(merchant);
        verify(merchantRepository).save(any());
    }

    @Test
    void findAllMerchantsTest() {
        service.findAllMerchants();
        verify(merchantRepository).findAll();
    }

    @Test
    void getMerchantAddressTest() {
        when(merchantRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getMerchantAddress(1L).getStatusCode());
        Merchant merchant = new Merchant();
        merchant.setAddress(new Address());
        when(merchantRepository.findById(any())).thenReturn(Optional.of(merchant));
        assertEquals(HttpStatus.OK, service.getMerchantAddress(1L).getStatusCode());
    }

    @Test
    void getMerchantProductsTest() {
        when(merchantRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getMerchantProducts(1L).getStatusCode());
        Merchant merchant = new Merchant();
        merchant.setProducts(Collections.singletonList(new Product()));
        when(merchantRepository.findById(any())).thenReturn(Optional.of(merchant));
        assertEquals(HttpStatus.OK, service.getMerchantProducts(1L).getStatusCode());
    }

    @Test
    void getOrdersOfMerchantTest() {
        when(merchantRepository.findById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getOrdersOfMerchant(1L).getStatusCode());
        Merchant merchant = new Merchant();
        merchant.setOrders(Collections.singletonList(new Order()));
        when(merchantRepository.findById(any())).thenReturn(Optional.of(merchant));
        assertEquals(HttpStatus.OK, service.getOrdersOfMerchant(1L).getStatusCode());
    }

    @Test
    void getMerchantFromProductIdTest() {
        when(productRepository.findProductById(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getMerchantFromProductId(1L).getStatusCode());
        Product product = new Product();
        product.setMerchant(new Merchant());
        when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        assertEquals(HttpStatus.OK, service.getMerchantFromProductId(1L).getStatusCode());
    }

    @Test
    void rateMerchant() {
        Merchant merchant = new Merchant();
        merchant.setRating(4D);
        merchant.setNumberOfRatings(1L);
        when(merchantRepository.findById(any())).thenReturn(Optional.of(merchant));
        service.rateMerchant(4D,1L);
        verify(merchantRepository).save(any());
    }
}
