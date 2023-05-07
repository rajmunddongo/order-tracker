package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MerchantController {

    ApiService apiService;

    @PostMapping("/product/merchant/{id}")
    public ResponseEntity<Product> addProducts(@RequestBody Product product, @PathVariable("id")Long id) {
        return apiService.postProduct(product,id);
    }

    @GetMapping("/merchant/{id}")
    public ResponseEntity<Merchant> getMerchantById(@PathVariable("id") Long id) {
        return apiService.findMerchantById(id);
    }

    @PostMapping("/merchant")
    public ResponseEntity<Merchant> saveMerchants(@RequestBody Merchant merchant) {
        apiService.saveMerchant(merchant);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/auth/merchants")
    public ResponseEntity<List<Merchant>> getMerchantById() {
        return new ResponseEntity<>(apiService.findAllMerchants(), HttpStatus.OK);
    }

    @GetMapping("/merchant/{id}/address")
    public ResponseEntity<Address> getMerchantsAddress(@PathVariable("id") Long id) {
        return apiService.getMerchantAddress(id);
    }

    @GetMapping("/merchant/{id}/products")
    public ResponseEntity<List<Product>> getMerchantProducts(@PathVariable("id") Long id) {
        return apiService.getMerchantProducts(id);
    }

    @GetMapping("/merchant/{id}/orders")
    public ResponseEntity<List<Order>> getOrdersOfMerchant(@PathVariable("id") Long id) {
        return apiService.getOrdersOfMerchant(id);
    }

    @GetMapping("/merchant/product/{id}")
    public ResponseEntity<Merchant> getMerchantFromProductId(@PathVariable("id") Long id) {
        return apiService.getMerchantFromProductId(id);
    }
}
