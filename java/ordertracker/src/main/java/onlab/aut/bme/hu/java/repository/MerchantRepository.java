package onlab.aut.bme.hu.java.repository;

import onlab.aut.bme.hu.java.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    public Optional<Merchant> findById(Long id);

    Optional<Merchant> findByUser_Email(@NonNull String email);

}
