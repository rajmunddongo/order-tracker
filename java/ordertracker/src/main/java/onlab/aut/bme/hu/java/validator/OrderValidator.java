package onlab.aut.bme.hu.java.validator;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderValidator {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    public void validateOrderRequest(Long customerId, Long merchantId, Order order) {
        if (customerRepository.findCustomerById(customerId).isPresent()) {
            order.setCustomer(customerRepository.findCustomerById(customerId).get());
        } else {
            throw new IllegalArgumentException("Bad customerId given: " + customerId);
        }
        if (merchantRepository.findById(merchantId).isPresent()) {
            order.setMerchant(merchantRepository.findById(merchantId).get());
        } else {
            throw new IllegalArgumentException("Bad merchantId given: " + merchantId);
        }
    }
}
