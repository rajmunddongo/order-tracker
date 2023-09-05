package onlab.aut.bme.hu.java;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.Customer;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import onlab.aut.bme.hu.java.provider.ShoppingCartProvider;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Tag("UnitTest")
public class UnitTest {

    private final ShoppingCartProvider shoppingCartProvider;

    public UnitTest() {
        shoppingCartProvider = new ShoppingCartProvider();
    }

    @Test
    public void testValidator() {
        Customer customer = new Customer();
        ShoppingCart shoppingCart = shoppingCartProvider.provide(customer);
        assertEquals(shoppingCart.getCustomer(), customer);
    }
}
