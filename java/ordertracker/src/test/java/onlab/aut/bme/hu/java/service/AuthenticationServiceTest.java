package onlab.aut.bme.hu.java.service;

import jakarta.inject.Inject;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.model.AuthenticationRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
        service.authenticate(request);
    }

    @Test
    void getUserFromJWTTest() {
        assertEquals(HttpStatus.NOT_FOUND,service.getUserFromJWT("awdawdawdawdawdawdawdawdawawd").getStatusCode());
    }
}
