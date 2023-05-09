package onlab.aut.bme.hu.java.repository;
import java.util.List;
import java.util.Optional;

import onlab.aut.bme.hu.java.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t inner join User u " +
            "on t.user.id = u.id " +
            "where u.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}