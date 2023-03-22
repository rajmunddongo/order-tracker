package onlab.aut.bme.hu.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    String type;

    String status;

    @OneToOne
    @JoinColumn(name = "customer_order_id")
    private Order orders;

    @JsonIgnore
    @OneToMany(mappedBy = "delivery")
    private List<Product> products;
}