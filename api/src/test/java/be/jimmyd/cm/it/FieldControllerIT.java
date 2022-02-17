package be.jimmyd.cm.it;


import be.jimmyd.cm.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:cm_field;DB_CLOSE_DELAY=-1")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FieldControllerIT {

    private final String mail = "test@test.be";
    private final String password = "Testpassword12345789";
    private final String username = "Test user";
    TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/" + uri;
    }

    private HttpHeaders getHeaders() {
        UserLoginDto login = new UserLoginDto.Builder()
                .withEmail(mail)
                .withPassword(password)
                .build();

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLogin.getBody().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @BeforeAll
    public void init() {

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        UserRegisterDto user = new UserRegisterDto.Builder()
                .withEmail(mail)
                .withFullName(username)
                .withPassword(password)
                .withConfirmPassword(password)
                .build();

        restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);

        FieldDto field = new FieldDto.Builder()
                .withName("custom field")
                .withLabel("Field label")
                .withPlaceholder("Placeholder")
                .withType("text")
                .withWidget("default")
                .withPlace("main")
                .build();

        List<FieldDto> fieldDtoList = new ArrayList<>();
        fieldDtoList.add(field);

        CollectionDto collectionDto = new CollectionDto.Builder()
                .withName("Movie collection")
                .withType("Movies")
                .withFields(fieldDtoList)
                .build();

        restTemplate.postForEntity(createURLWithPort("collection/add"), new HttpEntity<>(collectionDto, getHeaders()), Object.class);
    }

    @Order(1)
    @Test
    public void getByCollectionTest() {

        final ResponseEntity<FieldDto[]> result = restTemplate.exchange(createURLWithPort("field/collection/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), FieldDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(11, result.getBody().length);
        assertTrue(Arrays.asList(result.getBody()).stream().filter(n -> n.getId() == 48).filter(n -> n.getName().equals("custom field")).findFirst().isPresent());
    }

    @Order(2)
    @Test
    public void getBasicByCollectionTest() {

        final ResponseEntity<FieldDto[]> result = restTemplate.exchange(createURLWithPort("field/basic/collection/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), FieldDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(10, result.getBody().length);
        assertFalse(Arrays.asList(result.getBody()).stream().filter(n -> n.getId() == 48).filter(n -> n.getName().equals("custom field")).findFirst().isPresent());
    }

    @Order(3)
    @Test
    public void getCustomByCollectionTest() {

        final ResponseEntity<FieldDto[]> result = restTemplate.exchange(createURLWithPort("field/custom/collection/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), FieldDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(1, result.getBody().length);
        assertTrue(Arrays.asList(result.getBody()).stream().filter(n -> n.getId() == 48).filter(n -> n.getName().equals("custom field")).findFirst().isPresent());
    }
}
