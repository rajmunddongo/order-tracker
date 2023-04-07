package onlab.aut.bme.hu.java.controller;


import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/order/customer/{customerId}/merchant/{merchantId}")
    public ResponseEntity postOrder(@RequestBody Order order, @PathVariable("customerId") Long customerId, @PathVariable("merchantId") Long merchantId) {
        return new ResponseEntity(orderService.saveOrder(order, customerId, merchantId),HttpStatus.OK);
    }
    @GetMapping("/orders")
    public ResponseEntity getOrders() {
        return  new ResponseEntity(orderService.listOrders(),HttpStatus.OK);
    }
}
