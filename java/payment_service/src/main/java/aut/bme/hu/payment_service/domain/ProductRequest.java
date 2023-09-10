package aut.bme.hu.payment_service.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ProductRequest {
    private ArrayList<Product> products;
    private String currency;
}