package onlab.aut.bme.hu.java.service;

import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.Address;
import onlab.aut.bme.hu.java.model.Customer;
import onlab.aut.bme.hu.java.model.Merchant;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
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

    @Autowired
    MerchantRepository merchantRepository;

    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id).orElseThrow();
    }

    public void saveCustomer(Customer customer) {
        addressRepository.save(customer.getAddress());
        customerRepository.save(customer);
    }

    public Address findCustomerAddressById(Long id) {
        Customer customer = customerRepository.findCustomerById(id).orElseThrow();
        return customer.getAddress();
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Merchant> findAllMerchants() {
        return merchantRepository.findAll();
    }

    public Merchant findMerchantById(Long id) {
        Merchant merchant = merchantRepository.findById(id).orElseThrow();
        return merchant;
    }

    public void saveMerchant(Merchant merchant) {
        addressRepository.save(merchant.getAddress());
        merchantRepository.save(merchant);
    }


}
