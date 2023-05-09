package onlab.aut.bme.hu.java.utils;

import lombok.experimental.UtilityClass;
import onlab.aut.bme.hu.java.entity.Product;
import onlab.aut.bme.hu.java.entity.ShoppingCart;

import java.util.List;

@UtilityClass
public class CustomerUtils {

    public static boolean shoppingcartContainsProduct(Product product, ShoppingCart shoppingCart) {
        if (product.getId() == null) return false;
        if (shoppingCart.getProducts() != null)
            for (Product prod : shoppingCart.getProducts()) {
                if (product.getId().equals(prod.getId())) return true;
            }
        return false;
    }

    public static void removeProductById(List<Product> products, Long id) {
        Product remove = null;
        for (Product product : products) {
            if (product.getId().equals(id)) {
                remove = product;
            }
        }
        products.remove(remove);
    }
}
