package onlab.aut.bme.hu.java.domain;

import lombok.Builder;
import lombok.Data;
import onlab.aut.bme.hu.java.entity.Product;

import java.util.List;

@Data
@Builder
public class GetPaymentUrlRequest {

    private List<Product> products;
    private String currency;
}
