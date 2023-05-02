package onlab.aut.bme.hu.java.controller;

import onlab.aut.bme.hu.java.entity.*;
import onlab.aut.bme.hu.java.service.ApiService;
import onlab.aut.bme.hu.java.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/customer")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        apiService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity getLoggedin() {
        return new ResponseEntity(true,HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity getCustomer(@PathVariable("id") Long id) {
        Customer customer = apiService.findCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/customer/ids")
    public ResponseEntity getCustomerIds() {
        List<Customer> customers = apiService.findAllCustomers();
        List<Long> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getId());
        }
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers() {
        return new ResponseEntity<>(apiService.findAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/customer/{id}/address")
    public ResponseEntity getCustomerAddress(@PathVariable("id") Long id) {
        Address address = apiService.findCustomerAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity getCustomerAddresses() {
        return new ResponseEntity<>(apiService.findAllAddresses(), HttpStatus.OK);
    }


    @GetMapping("/auth/merchants")
    public ResponseEntity getMerchants() {
        return new ResponseEntity<>(apiService.findAllMerchants(), HttpStatus.OK);
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

    @GetMapping("/merchant/{id}/address")
    public ResponseEntity getMerchantsAddress(@PathVariable("id") Long id) {
        return apiService.getMerchantAddress(id);
    }

    @GetMapping("/products")
    public ResponseEntity getProducts() {
        return new ResponseEntity<>(apiService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<>(apiService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/product/merchant/{id}")
    public ResponseEntity addProducts(@RequestBody Product product,@PathVariable("id")Long id) {
        return apiService.postProduct(product,id);
    }

    @GetMapping("/shoppingcarts")
    public ResponseEntity getShoppingCarts() {
        return apiService.getShoppingCarts();
    }

    @GetMapping("/shoppingcart/{id}")
    public ResponseEntity getShoppingCarts(@PathVariable("id") Long id) {
        return apiService.getShoppingCart(id);
    }

    @PostMapping("/shoppingcart")
    public ResponseEntity postShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return apiService.saveShoppingCart(shoppingCart);
    }

    @GetMapping("/customer/{id}/shoppingcart/products")
    public ResponseEntity getCustomerShoppingCartProducts(@PathVariable("id") Long id) {
        return apiService.getCustomerShoppingCartProducts(id);
    }

    @PostMapping("/customer/{id}/shoppingcart/product")
    public ResponseEntity addToCustomerShoppingCartProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return apiService.addToCustomerShoppingCartProduct(product, id);
    }

    @DeleteMapping("/customer/{custId}/shoppingcart/product/{id}")
    public ResponseEntity deleteProductFromCart(@PathVariable("id") Long prodId, @PathVariable("custId") Long custId) {
        return apiService.deleteProductFromCart(prodId, custId);
    }

    @GetMapping("/merchant/{id}/orders")
    public ResponseEntity getOrdersOfMerchant(@PathVariable("id") Long id) {
        return apiService.getOrdersOfMerchant(id);
    }

    @GetMapping("/m/connect")
    public void connectMerchant() {
        apiService.connectMerchant();
    }

    @GetMapping("/order/{id}/customer")
    public ResponseEntity getOrderCustomer(@PathVariable("id") Long id) {
        return apiService.getOrderCustomer(id);
    }

    @PatchMapping("/order/{id}/status")
    public ResponseEntity patchOrderStatus(@PathVariable("id") Long id, @RequestBody String status) {
        return apiService.patchOrderStatus(id, status);
    }

    @GetMapping("/merchant/{id}/products")
    public ResponseEntity getMerchantProducts(@PathVariable("id") Long id) {
        return apiService.getMerchantProducts(id);
    }

    @GetMapping("/shoppingcart/{id}/products")
    public ResponseEntity getShoppingCartProducts(@PathVariable("id") Long id) {
        return apiService.getShoppingCartProducts(id);
    }
    @GetMapping("/order/{id}/delivery")
    public ResponseEntity getOrderDelivery(@PathVariable("id") Long id) {
        return apiService.getOrderDelivery(id);
    }

    @GetMapping("/merchant/product/{id}")
    public ResponseEntity getMerchantFromProductId(@PathVariable("id") Long id) {
        return apiService.getMerchantFromProductId(id);
    }

    @GetMapping("/customer/{id}/shoppingcart/orderId")
    public ResponseEntity getShoppingCartOrderId(@PathVariable("id") Long id) {
        return  apiService.getCustomerShoppingCartOrderId(id);
    }

}
