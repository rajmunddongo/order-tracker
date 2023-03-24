package onlab.aut.bme.hu.java.controller;

import jakarta.websocket.server.PathParam;
import onlab.aut.bme.hu.java.model.*;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/customer")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        authorizationService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity getCustomer(@PathVariable("id") Long id) {
        Customer customer = authorizationService.findCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/customer/ids")
    public ResponseEntity getCustomerIds() {
        List<Customer> customers = authorizationService.findAllCustomers();
        List<Long> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getId());
        }
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers() {
        return new ResponseEntity<>(authorizationService.findAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/customer/{id}/address")
    public ResponseEntity getCustomerAddress(@PathVariable("id") Long id) {
        Address address = authorizationService.findCustomerAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity getCustomerAddresses() {
        return new ResponseEntity<>(authorizationService.findAllAddresses(), HttpStatus.OK);
    }


    @GetMapping("/merchants")
    public ResponseEntity getMerchants() {
        return new ResponseEntity<>(authorizationService.findAllMerchants(), HttpStatus.OK);
    }

    @GetMapping("/merchant/{id}")
    public ResponseEntity getMerchants(@PathVariable("id") Long id) {
        return new ResponseEntity<>(authorizationService.findMerchantById(id), HttpStatus.OK);
    }

    @PostMapping("/merchant")
    public ResponseEntity saveMerchants(@RequestBody Merchant merchant) {
        authorizationService.saveMerchant(merchant);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/merchant/{id}/address")
    public ResponseEntity getMerchantsAddress(@PathVariable("id") Long id) {
        return new ResponseEntity<>(authorizationService.findMerchantById(id).getAddress(), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity getProducts() {
        return new ResponseEntity<>(authorizationService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<>(authorizationService.getProductById(id), HttpStatus.OK);
    }
    @PostMapping("/product")
    public ResponseEntity addProducts(@RequestBody Product product) {
        return authorizationService.postProduct(product);
    }
    @GetMapping("/shoppingcarts")
    public ResponseEntity getShoppingCarts() {
        return authorizationService.getShoppingCarts();
    }
    @GetMapping("/shoppingcart/{id}")
    public ResponseEntity getShoppingCarts(@PathVariable("id") Long id) {
        return authorizationService.getShoppingCart(id);
    }
    @PostMapping("/shoppingcart")
    public ResponseEntity postShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return authorizationService.saveShoppingCart(shoppingCart);
    }
}
