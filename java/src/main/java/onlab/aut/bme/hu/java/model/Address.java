package onlab.aut.bme.hu.java.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "address")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String address;

    @Column
    private String zipCode;

    @Column
    private String country;

    @Column
    private String city;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<Merchant> merchants;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<Customer> customers;

}
