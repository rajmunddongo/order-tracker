package onlab.aut.bme.hu.java.service;

import jakarta.transaction.Transactional;
import onlab.aut.bme.hu.java.model.Customer;
import onlab.aut.bme.hu.java.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private CustomerRepository repo;

    @Autowired
    public CustomerUserDetailsService(CustomerRepository repo) {
        this.repo = repo;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<Customer> user = repo.findCustomerByUsername(username);
        if (!user.isPresent()) {
            return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        if (username.equals("admin")) {
            auth = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        }
        String password = user.get().getPassword();
        return new org.springframework.security.core.userdetails.User(username, password,
                auth);
    }

}