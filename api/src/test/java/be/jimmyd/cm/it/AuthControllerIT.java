package be.jimmyd.cm.it;

import be.jimmyd.cm.dto.TokenDto;
import be.jimmyd.cm.dto.UserLoginDto;
import be.jimmyd.cm.dto.UserRegisterDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:cm_auth;DB_CLOSE_DELAY=-1")
public class AuthControllerIT {

    private final String mail = "test@test.be";
    private final String password = "Testpassword12345789";
    TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/" + uri;
    }

    @Order(1)
    @Test
    public void testRegisterUser() {

        UserRegisterDto user = new UserRegisterDto.Builder()
                .withEmail(mail)
                .withPassword(password)
                .withFullName("Test user")
                .build();

        final ResponseEntity<Object> userRegistration = restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);

        assertEquals(HttpStatus.OK, userRegistration.getStatusCode());
    }

    @Test
    @Order(2)
    public void testLogin() {

        UserLoginDto login = new UserLoginDto.Builder()
                .withEmail(mail)
                .withPassword(password)
                .build();

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        assertEquals(HttpStatus.OK, userLogin.getStatusCode());
        assertTrue(userLogin.getBody().getToken() != null && !userLogin.getBody().getToken().isBlank());
    }

    @Test
    @Order(3)
    public void testWrongCredentialsLogin() {

        UserLoginDto login = new UserLoginDto.Builder()
                .withEmail(mail)
                .withPassword("WrongPassword")
                .build();

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        assertEquals(HttpStatus.UNAUTHORIZED, userLogin.getStatusCode());
    }

    @Order(4)
    @Test
    public void testRegisterUserAlreadyExists() {

        UserRegisterDto user = new UserRegisterDto.Builder()
                .withEmail(mail)
                .withPassword(password)
                .withFullName("Test user")
                .build();

        final ResponseEntity<Object> userRegistration = restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);

        assertEquals(HttpStatus.CONFLICT, userRegistration.getStatusCode());
    }
}
