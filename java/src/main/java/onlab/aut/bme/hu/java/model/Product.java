package onlab.aut.bme.hu.java.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
