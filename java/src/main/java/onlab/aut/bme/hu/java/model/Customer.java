package onlab.aut.bme.hu.java.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "customer")

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Order> orders;

    @OneToOne
    private ShoppingCart shoppingCart;


}
