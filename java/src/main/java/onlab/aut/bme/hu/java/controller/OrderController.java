package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/customer/{customerId}/merchant/{merchantId}")
    public ResponseEntity<Order> postOrder(@RequestBody ArrayList<Product> products, @PathVariable("customerId") Long customerId, @PathVariable("merchantId") Long merchantId) {
        return orderService.saveOrder(products, customerId, merchantId);
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        return orderService.getOrder(id);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return  new ResponseEntity<>(orderService.listOrders(),HttpStatus.OK);
    }
}
