package onlab.aut.bme.hu.java.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String imgSource;

    @Column
    private Long price;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    @JsonManagedReference("merchant-product")
    private Merchant merchant;

    @ManyToMany
    @JoinTable(name = "product_order",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> order;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    @JsonBackReference("product-delivery")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "shoppingcart_id")
    @JsonBackReference("product-shoppingcart")
    private ShoppingCart shoppingCart;
}
