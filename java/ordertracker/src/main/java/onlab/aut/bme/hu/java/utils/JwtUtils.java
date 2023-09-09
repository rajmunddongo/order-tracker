package onlab.aut.bme.hu.java.utils;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.repository.UserRepository;
import onlab.aut.bme.hu.java.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class JwtUtils {
    private final UserRepository repository;
    private final JwtService jwtService;

    public ResponseEntity<User> getUserFromJWT(String token) {
        String email = jwtService.extractUsername(token.substring(7));
        Optional<User> user = repository.findByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
