package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {


    ApiService apiService;

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        apiService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        Customer customer = apiService.findCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/customer/ids")
    public ResponseEntity<List<Long>> getCustomerIds() {
        List<Customer> customers = apiService.findAllCustomers();
        List<Long> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getId());
        }
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(apiService.findAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/customer/{id}/address")
    public ResponseEntity<Address> getCustomerAddress(@PathVariable("id") Long id) {
        Address address = apiService.findCustomerAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}/shoppingcart/products")
    public ResponseEntity<List<Product>> getCustomerShoppingCartProducts(@PathVariable("id") Long id) {
        return apiService.getCustomerShoppingCartProducts(id);
    }

    @PostMapping("/customer/{id}/shoppingcart/product")
    public ResponseEntity<HttpStatus> addToCustomerShoppingCartProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return apiService.addToCustomerShoppingCartProduct(product, id);
    }

    @DeleteMapping("/customer/{custId}/shoppingcart/product/{id}")
    public ResponseEntity<ShoppingCart> deleteProductFromCart(@PathVariable("id") Long prodId, @PathVariable("custId") Long custId) {
        return apiService.deleteProductFromCart(prodId, custId);
    }

    @GetMapping("/order/{id}/customer")
    public ResponseEntity<Customer> getOrderCustomer(@PathVariable("id") Long id) {
        return apiService.getOrderCustomer(id);
    }

    @GetMapping("/customer/{id}/shoppingcart/orderId")
    public ResponseEntity<Long> getShoppingCartOrderId(@PathVariable("id") Long id) {
        return  apiService.getCustomerShoppingCartOrderId(id);
    }
}
