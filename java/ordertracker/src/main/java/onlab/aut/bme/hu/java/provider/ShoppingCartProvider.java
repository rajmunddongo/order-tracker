package onlab.aut.bme.hu.java.provider;

import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ShoppingCartProvider {

    public ShoppingCart provide(Customer customer) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        shoppingCart.setProducts(new ArrayList<>());
        return shoppingCart;
    }
}
