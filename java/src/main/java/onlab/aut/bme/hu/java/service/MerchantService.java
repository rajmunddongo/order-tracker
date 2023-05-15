package onlab.aut.bme.hu.java.service;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.Order;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.repository.AddressRepository;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import onlab.aut.bme.hu.java.repository.DeliveryRepository;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.OrderRepository;
import onlab.aut.bme.hu.java.repository.ProductRepository;
import onlab.aut.bme.hu.java.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<Product> postProduct(Product product, Long id) {
        if (merchantRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.setMerchant(merchantRepository.findById(id).get());
            productRepository.save(product);
        }
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    public ResponseEntity<Merchant> findMerchantById(Long id) {
        if (merchantRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(merchantRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void saveMerchant(Merchant merchant) {
        addressRepository.save(merchant.getAddress());
        if (merchant.getProducts() != null)
            productRepository.saveAll(merchant.getProducts());
        merchantRepository.save(merchant);
    }

    public List<Merchant> findAllMerchants() {
        return merchantRepository.findAll();
    }

    public ResponseEntity<Address> getMerchantAddress(Long id) {
        if (merchantRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(merchantRepository.findById(id).get().getAddress(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Product>> getMerchantProducts(Long id) {
        if (merchantRepository.findById(id).isPresent() && merchantRepository.findById(id).get().getProducts() != null) {
            Merchant merchant = merchantRepository.findById(id).get();
            return new ResponseEntity<>(merchant.getProducts(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Order>> getOrdersOfMerchant(Long id) {
        if (merchantRepository.findById(id).isPresent()) {
            Merchant merchant = merchantRepository.findById(id).get();
            return new ResponseEntity<>(merchant.getOrders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Merchant> getMerchantFromProductId(Long id) {
        if (productRepository.findProductById(id).isPresent() && productRepository.findProductById(id).get().getMerchant() != null) {
            return new ResponseEntity<>(productRepository.findProductById(id).get().getMerchant(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void rateMerchant(Double rate, Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId).get();
        Double rating = merchant.getRating();
        Long numOfRatings = merchant.getNumberOfRatings();
        rating = ((rating*numOfRatings)+rate)/(numOfRatings+1);
        numOfRatings++;
        BigDecimal bd = new BigDecimal(rating);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        double roundedNumber = bd.doubleValue();
        merchant.setRating(roundedNumber);
        merchant.setNumberOfRatings(numOfRatings);
        merchantRepository.save(merchant);
    }
}
