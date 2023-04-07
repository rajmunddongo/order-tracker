package onlab.aut.bme.hu.java.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
