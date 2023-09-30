package onlab.aut.bme.hu.java.utils;

import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CustomerUtilsTest {

    @Test
    void shoppingcartContainsProductTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = new Product();
        Product product2 = new Product();
        assertFalse(CustomerUtils.shoppingcartContainsProduct(product, shoppingCart));
        product.setId(1L);
        assertFalse(CustomerUtils.shoppingcartContainsProduct(product, shoppingCart));
        List<Product> productList = new ArrayList<>();
        productList.add(product2);
        productList.add(product);
        shoppingCart.setProducts(productList);
        assertTrue(CustomerUtils.shoppingcartContainsProduct(product, shoppingCart));
    }

    @Test
    void removeProductByIdTest() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        Product product2 = new Product();
        product2.setId(2L);
        product.setId(1L);
        productList.add(product2);
        productList.add(product);
        assertEquals(product2, productList.get(0));
        CustomerUtils.removeProductById(productList,1L);
        CustomerUtils.removeProductById(productList,2L);
        assertTrue(productList.isEmpty());
    }
}
