package onlab.aut.bme.hu.java.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class FileManagerServiceTest {

    RestTemplate restTemplate = new RestTemplate();
    MockRestServiceServer serviceServer = MockRestServiceServer.createServer(restTemplate);
    FileManagerService service = new FileManagerService(restTemplate, "ye", "https://www.google.com/");

    @Test
    void uploadFileThrowsTest() {
        MultipartFile multipartFiles = mock(MultipartFile.class);
        assertThrows(IllegalArgumentException.class, () -> {
            service.uploadFile(multipartFiles, "yes");
        });
    }

    @Test
    void uploadFileTest() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(), requestTo("/ye"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
                .andExpect(request -> {
                    ByteArrayOutputStream requestBody = (ByteArrayOutputStream) request.getBody();
                    byte[] bytes = requestBody.toByteArray();
                    assert(bytes.length > 0);
                })
                .andRespond(withStatus(HttpStatus.OK));

        service.uploadFile(multipartFile, "yes");
        mockServer.verify();
    }
    @Test
    void downloadFileTest() throws IOException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(), requestTo("https://www.google.com/yes"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK));
        service.downloadFile("yes");
        mockServer.verify();
    }

}
