package be.jimmyd.cm.it;

import be.jimmyd.cm.dto.RoleDto;
import be.jimmyd.cm.dto.TokenDto;
import be.jimmyd.cm.dto.UserLoginDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:cm_role;DB_CLOSE_DELAY=-1")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleControllerIT {

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
                .withPassword(password)
                .withEmail(mail)
                .build();

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLogin.getBody().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @BeforeAll
    public void init() {
        UserRegisterDto user = new UserRegisterDto.Builder()
                .withEmail(mail)
                .withFullName(username)
                .withPassword(password)
                .build();

        restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void getActiveRolesTest() {
        final ResponseEntity<RoleDto[]> roleResponse = restTemplate.exchange(createURLWithPort("role/active"), HttpMethod.GET, new HttpEntity<>(getHeaders()), RoleDto[].class);

        assertEquals(HttpStatus.OK, roleResponse.getStatusCode());
        assertEquals(3, Arrays.stream(roleResponse.getBody()).count());
    }
}
