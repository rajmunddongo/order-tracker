package aut.bme.hu.payment_service.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {

    private String name;
    private String price;
}
