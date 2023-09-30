package onlab.aut.bme.hu.java.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {


    @InjectMocks
    JwtService service;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(service, "secretKey" , "404E635266556A586E3272343238782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(service, "jwtExpiration" , 600000000);
        ReflectionTestUtils.setField(service, "refreshExpiration" , 600000000);
    }

    @Test
    void extractUsernameTest() {

        String jwt = Jwts.builder()
                .setSubject("test")
                .signWith(SignatureAlgorithm.HS256, "404E635266556A586E3272343238782F413F4428472B4B6250645367566B5970")
                .compact();
        String extractedUsername = service.extractUsername(jwt);
        assertEquals("test", extractedUsername);
    }

    @Test
    void generateTokenTest() {
        User user= new User("username","pass", Collections.singletonList(new SimpleGrantedAuthority("USER")));
        service.generateToken(user);
    }

    @Test
    void isTokenExpiredTest() {
        String jwt = Jwts.builder()
                .setSubject("test")
                .setExpiration(Date.valueOf(LocalDate.MAX))
                .signWith(SignatureAlgorithm.HS256, "404E635266556A586E3272343238782F413F4428472B4B6250645367566B5970")
                .compact();
        String jwt2 = Jwts.builder()
                .setSubject("test")
                .setExpiration(Date.valueOf(LocalDate.MIN))
                .signWith(SignatureAlgorithm.HS256, "404E635266556A586E3272343238782F413F4428472B4B6250645367566B5970")
                .compact();
        assertThrows(ExpiredJwtException.class, () -> {service.isTokenValid(jwt,User.withUsername("test").password("password").roles("USER").build());});
        assertTrue(service.isTokenValid(jwt2,User.withUsername("test").password("password").roles("USER").build()));
    }

}
