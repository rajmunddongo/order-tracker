package aut.bme.hu.payment_service.controller;

import aut.bme.hu.payment_service.domain.PostPaymentLinkResponse;
import aut.bme.hu.payment_service.domain.PostProductResponse;
import aut.bme.hu.payment_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/product/{name}")
    public ResponseEntity<String> postProduct(@PathVariable("name") String name) {
        return productService.postProduct(name);
    }
}
