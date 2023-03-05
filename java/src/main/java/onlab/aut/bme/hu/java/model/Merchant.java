package onlab.aut.bme.hu.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private Double rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


}
