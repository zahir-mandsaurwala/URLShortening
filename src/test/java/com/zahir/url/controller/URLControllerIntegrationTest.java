package com.zahir.url.controller;

import com.zahir.url.dto.URLResponseDto;
import com.zahir.url.entity.URL;
import com.zahir.url.repository.URLRepository;
import com.zahir.url.service.URLServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class URLControllerIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private URLServiceImpl urlServiceImpl;

    @MockBean
    private URLRepository urlRepository;

    @Test
    public void testGetOriginalURL() throws Exception {

        URL url1 = new URL(1L, "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json",
                "d827ecd7", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(urlRepository.findByShortURL("d827ecd7")).thenReturn(url1);

        assertTrue(urlServiceImpl.getOriginalURL("d827ecd7").getOriginalURL().equals("https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json"));
    }

    @Test
    public void testSaveURL() {
        String originalURL = "https://stash.backbase.com/projects/P1/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";

        ResponseEntity<URLResponseDto> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/short?url="+originalURL, null, URLResponseDto.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

}
