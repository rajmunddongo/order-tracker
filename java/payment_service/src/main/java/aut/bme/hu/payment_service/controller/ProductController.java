package aut.bme.hu.payment_service.controller;

import aut.bme.hu.payment_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/product")
    public ResponseEntity<String> postProduct(@RequestBody ProductRequest request) {
        return productService.getPaymentUrl(request.getProducts(), request.getCurrency());
    }
}
