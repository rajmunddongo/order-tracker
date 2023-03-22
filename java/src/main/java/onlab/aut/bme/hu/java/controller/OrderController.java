package onlab.aut.bme.hu.java.controller;


import onlab.aut.bme.hu.java.model.Order;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity postOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/orders")
    public ResponseEntity getOrders() {
        return  new ResponseEntity(orderService.listOrders(),HttpStatus.OK);
    }
}
