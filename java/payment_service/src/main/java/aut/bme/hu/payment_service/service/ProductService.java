package aut.bme.hu.payment_service.service;

import aut.bme.hu.payment_service.domain.PostProductResponse;
import aut.bme.hu.payment_service.entity.Product;
import aut.bme.hu.payment_service.repository.CredentialsRepository;
import aut.bme.hu.payment_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ProductRepository productRepository;
    private final CredentialsRepository credentialsRepository;


    public ResponseEntity<String> postProduct(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer " + credentialsRepository.findById("1").orElseThrow().getPrivateKey());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", name);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<PostProductResponse> response = restTemplate
                .exchange("https://api.stripe.com/v1/products", HttpMethod.POST, entity, PostProductResponse.class);
        persistProduct(Objects.requireNonNull(response.getBody()));
        return new ResponseEntity<>(response.getBody().toString(),HttpStatus.OK);
    }

    private void persistProduct(PostProductResponse response) {
        Product product = Product.builder()
                .id(response.getId())
                .name(response.getName())
                .build();
        productRepository.save(product);
    }
}
