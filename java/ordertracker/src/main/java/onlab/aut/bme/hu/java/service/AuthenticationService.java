package onlab.aut.bme.hu.java.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.*;
import onlab.aut.bme.hu.java.model.AuthenticationRequest;
import onlab.aut.bme.hu.java.model.AuthenticationResponse;
import onlab.aut.bme.hu.java.model.RegisterRequest;
import onlab.aut.bme.hu.java.repository.*;
import onlab.aut.bme.hu.java.model.enums.TokenType;
import onlab.aut.bme.hu.java.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final ApiService apiService;

    private final CustomerService customerService;

    private final MerchantService merchantService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Customer customer = request.getCustomer();
        return getAuthenticationResponse(customer, request.getFirstname(), request.getLastname(), request.getEmail(), request.getPassword());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public ResponseEntity<User> getUserFromJWT(String token) {
        String email = jwtService.extractUsername(token.substring(7));
        Optional<User> user = repository.findByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public AuthenticationResponse registerCustomer(User user) {
        Customer customer = new Customer();
        customer.setAddress(user.getCustomer().getAddress());
        return getAuthenticationResponse(customer, user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
    }

    private AuthenticationResponse getAuthenticationResponse(Customer customer, String firstname, String lastname, String email, String password) {
        User authUser = User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .profilePicture("customericon")
                .role(Role.USER)
                .customer(customer)
                .build();
        customerService.saveCustomer(customer);
        User savedUser = repository.save(authUser);
        customer.setUser(authUser);
        customerService.saveCustomer(customer);
        String jwtToken = jwtService.generateToken(authUser);
        String refreshToken = jwtService.generateRefreshToken(authUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse registerMerchant (User user) {
        Merchant merchant =  new Merchant();
        merchant.setNumberOfRatings(0L);
        merchant.setName(user.getMerchant().getName());
        merchant.setAddress(user.getMerchant().getAddress());
        User authUser = User.builder()
                .firstname("Merchant")
                .lastname("Merchant")
                .email(user.getEmail())
                .profilePicture("defMerchant")
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.MERCHANT)
                .merchant(merchant)
                .build();
        merchantService.saveMerchant(merchant);
        User savedUser = repository.save(authUser);
        merchant.setUser(authUser);
        merchantService.saveMerchant(merchant);
        String jwtToken = jwtService.generateToken(authUser);
        String refreshToken = jwtService.generateRefreshToken(authUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ResponseEntity<Product> addProductToMerchant(Product product, String header) {
        User user = this.getUserFromJWT(header).getBody();
        if(user == null) throw new IllegalStateException("Cannot add");
        Merchant merchant = user.getMerchant();
        productRepository.save(product);
        product.setMerchant(merchant);
        if (merchant.getProducts() == null) {
            merchant.setProducts(new ArrayList<>());
        }
        merchant.getProducts().add(product);
        merchantRepository.save(merchant);
        productRepository.save(product);
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }
}