package onlab.aut.bme.hu.java.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "shoppingcart")
@Setter
@Getter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JsonIgnore
    @JoinColumn(name = "shoppingcart_id")
    private List<Product> products;

    @OneToOne
    private Customer customer;

    private Long orderId;

    private BigDecimal couponPrecentage;

    public List<Product> getProducts() {
        return products;
    }

    public ShoppingCart() {
        couponPrecentage= BigDecimal.ZERO;
    }
}
