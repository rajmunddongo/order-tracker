package onlab.aut.bme.hu.java.controller;

import jakarta.websocket.server.PathParam;
import onlab.aut.bme.hu.java.model.Address;
import onlab.aut.bme.hu.java.model.Customer;
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
    @GetMapping("/customers")
    public ResponseEntity getCustomers() {
        List<Customer> customers = authorizationService.findAllCustomers();
        List<Long> ids = new ArrayList<>();
        for(Customer customer : customers){
            ids.add(customer.getId());
        }
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }
    @GetMapping("/customer/{id}/address")
    public ResponseEntity getCustomerAddress(@PathVariable("id") Long id) {
        Address address = authorizationService.findAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @GetMapping("/addresses")
    public ResponseEntity getCustomerAddresses() {
        return new ResponseEntity<>(authorizationService.findAllAddresses(), HttpStatus.OK);
    }
}
