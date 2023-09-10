package aut.bme.hu.payment_service.domain;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class Product {

    private String name;
    private String price;
}
