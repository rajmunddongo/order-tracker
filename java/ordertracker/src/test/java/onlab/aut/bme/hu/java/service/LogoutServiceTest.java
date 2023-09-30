package onlab.aut.bme.hu.java.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import onlab.aut.bme.hu.java.entity.Token;
import onlab.aut.bme.hu.java.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class LogoutServiceTest {

    @InjectMocks
    LogoutService service;
    @Mock
    TokenRepository tokenRepository;

    @Test
    void logoutTest() {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
        when(tokenRepository.findByToken(any())).thenReturn(Optional.of(new Token()));
        service.logout(httpServletRequest,httpServletResponse,authentication);
        when(httpServletRequest.getHeader(any())).thenReturn("Bearer myhawdawdwadwadwadwadawdawdawdeader");
        service.logout(httpServletRequest,httpServletResponse,authentication);
        verify(tokenRepository).save(any());
    }
}
