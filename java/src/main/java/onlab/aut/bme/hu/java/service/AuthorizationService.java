package onlab.aut.bme.hu.java.service;

import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.Address;
import onlab.aut.bme.hu.java.model.Customer;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AuthorizationService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id).orElseThrow();
    }

    public void saveCustomer(Customer customer) {
        addressRepository.save(customer.getAddress());
        customerRepository.save(customer);
    }
    public Address findAddressById(Long id) {
        Customer customer = customerRepository.findCustomerById(id).orElseThrow();
        return customer.getAddress();
    }
    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

}
