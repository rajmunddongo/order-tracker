package aut.bme.hu.payment_service.service;

import aut.bme.hu.payment_service.domain.PostPaymentLinkResponse;
import aut.bme.hu.payment_service.domain.PostPriceResponse;
import aut.bme.hu.payment_service.domain.PostProductResponse;
import aut.bme.hu.payment_service.entity.Product;
import aut.bme.hu.payment_service.repository.CredentialsRepository;
import aut.bme.hu.payment_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ProductRepository productRepository;
    private final CredentialsRepository credentialsRepository;

    public ResponseEntity<String> getPaymentUrl(ArrayList<aut.bme.hu.payment_service.domain.Product> products, String currency) {
        ArrayList<PostPriceResponse> postPriceResponses = new ArrayList<>();
        for(aut.bme.hu.payment_service.domain.Product product : products) {
            System.out.println(product.toString());
            PostProductResponse postProductResponse = postProduct(product.getName());
            PostPriceResponse postPriceResponse = postPrice(product.getPrice()+"00",postProductResponse.getId(),currency);
            postPriceResponses.add(postPriceResponse);
        }
        return ResponseEntity.ok(postPaymentLinkResponse(postPriceResponses).getUrl());
    }


    private PostProductResponse postProduct(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer " + credentialsRepository.findById("1").orElseThrow().getPrivateKey());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", name);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<PostProductResponse> response = restTemplate
                .exchange("https://api.stripe.com/v1/products", HttpMethod.POST, entity, PostProductResponse.class);
        persistProduct(Objects.requireNonNull(response.getBody()));
        return response.getBody();
    }

    private void persistProduct(PostProductResponse response) {
        Product product = Product.builder()
                .id(response.getId())
                .name(response.getName())
                .build();
        productRepository.save(product);
    }

    private PostPriceResponse postPrice(String price, String product, String currency) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer " + credentialsRepository.findById("1").orElseThrow().getPrivateKey());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("product", product);
        map.add("currency", currency);
        map.add("unit_amount", price);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<PostPriceResponse> response = restTemplate
                .exchange("https://api.stripe.com/v1/prices", HttpMethod.POST, entity, PostPriceResponse.class);

        persistProductPriceInfo(price, product, currency, response);

        return response.getBody();
    }

    private void persistProductPriceInfo(String price, String product, String currency
            ,ResponseEntity<PostPriceResponse> response) {
        Product prod = productRepository.findById(product).orElseThrow();
        prod.setPriceId(response.getBody().getId());
        prod.setCurrency(currency);
        prod.setAmount(new BigDecimal(price));
        productRepository.save(prod);
    }

    private PostPaymentLinkResponse postPaymentLinkResponse(ArrayList<PostPriceResponse> postPriceResponses) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer " + credentialsRepository.findById("1").orElseThrow().getPrivateKey());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for(int i = 0; i < postPriceResponses.size(); i++) {
            map.add("line_items["+ i + "][quantity]", "1");
            map.add("line_items["+ i + "][price]", postPriceResponses.get(i).getId());
        }
        map.add("after_completion[type]","redirect");
        map.add("after_completion[redirect][url]","http://localhost:4200/");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<PostPaymentLinkResponse> response = restTemplate
                .exchange("https://api.stripe.com/v1/payment_links", HttpMethod.POST, entity, PostPaymentLinkResponse.class);

        return response.getBody();
    }
}
