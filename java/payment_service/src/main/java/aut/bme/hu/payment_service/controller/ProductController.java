package aut.bme.hu.payment_service.controller;

import aut.bme.hu.payment_service.domain.PostPaymentLinkResponse;
import aut.bme.hu.payment_service.domain.PostProductResponse;
import aut.bme.hu.payment_service.domain.Product;
import aut.bme.hu.payment_service.domain.ProductRequest;
import aut.bme.hu.payment_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/product")
    public ResponseEntity<String> postProduct(@RequestBody ProductRequest request) {
        return productService.getPaymentUrl(request.getProducts(), request.getCurrency());
    }
}
