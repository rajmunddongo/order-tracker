package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.*;
import onlab.aut.bme.hu.java.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @GetMapping("/login")
    public ResponseEntity<Boolean> getLoggedin() {
        return new ResponseEntity<>(true,HttpStatus.OK);
    }


    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getCustomerAddresses() {
        return new ResponseEntity<>(apiService.findAllAddresses(), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(apiService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        return apiService.getProductById(id);
    }

    @GetMapping("/shoppingcarts")
    public ResponseEntity<List<ShoppingCart>> getShoppingCarts() {
        return apiService.getShoppingCarts();
    }

    @GetMapping("/shoppingcart/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCarts(@PathVariable("id") Long id) {
        return apiService.getShoppingCart(id);
    }

    @PostMapping("/shoppingcart")
    public ResponseEntity<ShoppingCart> postShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return apiService.saveShoppingCart(shoppingCart);
    }

    @GetMapping("/m/connect")
    public void connectMerchant() {
        apiService.connectMerchant();
    }

    @PatchMapping("/order/{id}/status")
    public ResponseEntity<Order> patchOrderStatus(@PathVariable("id") Long id, @RequestBody String status) {
        return apiService.patchOrderStatus(id, status);
    }

    @GetMapping("/shoppingcart/{id}/products")
    public ResponseEntity<List<Product>> getShoppingCartProducts(@PathVariable("id") Long id) {
        return apiService.getShoppingCartProducts(id);
    }
    @GetMapping("/order/{id}/delivery")
    public ResponseEntity<Delivery> getOrderDelivery(@PathVariable("id") Long id) {
        return apiService.getOrderDelivery(id);
    }

}
