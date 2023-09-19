package onlab.aut.bme.hu.java.repository;


import java.util.Optional;

import onlab.aut.bme.hu.java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPassResetLink(String resetLink);

}