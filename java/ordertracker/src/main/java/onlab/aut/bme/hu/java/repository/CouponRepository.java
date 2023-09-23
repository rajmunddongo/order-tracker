package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, BigDecimal> {

    public Optional<Coupon> findCouponByCode(String code);
}
