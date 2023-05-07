package onlab.aut.bme.hu.java.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.model.AuthenticationRequest;
import onlab.aut.bme.hu.java.model.AuthenticationResponse;
import onlab.aut.bme.hu.java.service.AuthenticationService;
import onlab.aut.bme.hu.java.model.RegisterRequest;
import onlab.aut.bme.hu.java.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/customer/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @RequestBody User user
    ) {
        return ResponseEntity.ok(service.registerCustomer(user));
    }

    @PostMapping("/merchant/register")
    public ResponseEntity<AuthenticationResponse> registerMerchant(
            @RequestBody User user
    ) {
        return ResponseEntity.ok(service.registerMerchant(user));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/whoami")
    public ResponseEntity whoAmI(@RequestHeader("Authorization") String authorizationHeader) {
        return service.getUserFromJWT(authorizationHeader);
    }

    @PostMapping("/merchant/product")
    public ResponseEntity addProductToMerchant(@RequestBody Product product, @RequestHeader("Authorization") String authorizationHeader) {
        return service.addProductToMerchant(product, authorizationHeader);
    }


}