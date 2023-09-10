package aut.bme.hu.payment_service.repository;

import aut.bme.hu.payment_service.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {
}
