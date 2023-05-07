package onlab.aut.bme.hu.java.controller;

import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.service.ApiService;
import onlab.aut.bme.hu.java.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class MerchantController {

    @Autowired
    ApiService apiService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/product/merchant/{id}")
    public ResponseEntity addProducts(@RequestBody Product product, @PathVariable("id")Long id) {
        return apiService.postProduct(product,id);
    }

    @GetMapping("/merchant/{id}")
    public ResponseEntity getMerchants(@PathVariable("id") Long id) {
        return apiService.findMerchantById(id);
    }

    @PostMapping("/merchant")
    public ResponseEntity saveMerchants(@RequestBody Merchant merchant) {
        apiService.saveMerchant(merchant);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/auth/merchants")
    public ResponseEntity getMerchants() {
        return new ResponseEntity<>(apiService.findAllMerchants(), HttpStatus.OK);
    }

    @GetMapping("/merchant/{id}/address")
    public ResponseEntity getMerchantsAddress(@PathVariable("id") Long id) {
        return apiService.getMerchantAddress(id);
    }

    @GetMapping("/merchant/{id}/products")
    public ResponseEntity getMerchantProducts(@PathVariable("id") Long id) {
        return apiService.getMerchantProducts(id);
    }

    @GetMapping("/merchant/{id}/orders")
    public ResponseEntity getOrdersOfMerchant(@PathVariable("id") Long id) {
        return apiService.getOrdersOfMerchant(id);
    }

    @GetMapping("/merchant/product/{id}")
    public ResponseEntity getMerchantFromProductId(@PathVariable("id") Long id) {
        return apiService.getMerchantFromProductId(id);
    }
}
