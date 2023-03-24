package onlab.aut.bme.hu.java.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "address")
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

    @JsonManagedReference("address-merchant")
    @OneToMany(mappedBy = "address")
    private List<Merchant> merchants;

    @JsonManagedReference("address-customer")
    @OneToMany(mappedBy = "address")
    private List<Customer> customers;

}
