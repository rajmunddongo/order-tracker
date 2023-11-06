package onlab.aut.bme.hu.java.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.model.AuthenticationRequest;
import onlab.aut.bme.hu.java.model.AuthenticationResponse;
import onlab.aut.bme.hu.java.model.RegisterRequest;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import onlab.aut.bme.hu.java.repository.TokenRepository;
import onlab.aut.bme.hu.java.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationService service;
    @Mock
    UserRepository userRepository;
    @Mock
    TokenRepository tokenRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @Mock
    ApiService apiService;
    @Mock
    CustomerService customerService;
    @Mock
    MerchantService merchantService;
    @Mock
    AuthenticationManager authenticationManager;

    @Test
    void registerTest() {
        RegisterRequest request = new RegisterRequest();
        request.setCustomer(new Customer());
        service.register(request);
    }

    @Test
    void authenticate() {
        AuthenticationRequest request = new AuthenticationRequest();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));
        service.login(request);
    }

    @Test
    void getUserFromJWTTest() {
        assertEquals(HttpStatus.NOT_FOUND,service.getUserFromJWT("awdawdawdawdawdawdawdawdawawd").getStatusCode());
    }

    @Test
    void refreshTokenTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String refreshToken = "sampleRefreshToken";
        String userEmail = "test@example.com";
        String accessToken = "sampleAccessToken";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
        when(jwtService.extractUsername(refreshToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(java.util.Optional.of(new User()));
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(servletOutputStream);
        when(jwtService.createAccessToken(any())).thenReturn(accessToken);
        service.refreshToken(request, response);
        verify(response).getOutputStream();
        verify(jwtService).createAccessToken(any(User.class));
    }

    @Test
    void registerCustomerTest() {
        User user = new User();
        Customer customer = new Customer();
        customer.setAddress(new Address());
        user.setCustomer(customer);
        assertEquals(AuthenticationResponse.class,service.registerCustomer(user).getClass());
    }

    @Test
    void registerMerchantTest() {
        User user = new User();
        Merchant merchant = new Merchant();
        merchant.setAddress(new Address());
        user.setMerchant(merchant);
        assertEquals(AuthenticationResponse.class,service.registerMerchant(user).getClass());
    }

    @Test
    void addProductToMerchantTest() {
        Product product = new Product();
        User user = new User();
        Merchant merchant = new Merchant();
        user.setMerchant(merchant);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        assertEquals(HttpStatus.OK, service.addProductToMerchant(product, "awawdddddddddda").getStatusCode());
    }
}
