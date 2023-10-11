package aut.bme.hu.payment_service.service;



import aut.bme.hu.payment_service.entity.Credentials;
import aut.bme.hu.payment_service.repository.CredentialsRepository;
import aut.bme.hu.payment_service.repository.ProductRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.model.PostProductResponse;
import org.openapitools.client.model.Product;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService service;

    @Mock
    RestTemplate restTemplate;
    @Mock
    ProductRepository productRepository;
    @Mock
    CredentialsRepository credentialsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPaymentUrlTest() {
        Product product = new Product();
        product.setName("Prod");
        product.setPrice("123");
        Credentials cred = new Credentials();
        cred.setId(1L);
        cred.setPrivateKey("dawawd");
        cred.setPublicKey("13213awd");

        when(credentialsRepository.findById(any())).thenReturn(Optional.of(cred));
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                service.getPaymentUrl(Collections.singletonList(product), "HUF"));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }
}
