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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIT {

    @LocalServerPort
    private int port;

    private final String mail = "test@test.be";
    private final String password = "Testpassword12345789";
    private final String username = "Test user";

    private String newUser = "New user";
    private final String newMail = "newMail@test.be";
    private final String newPassword = "NewPassword123456789";

    TestRestTemplate restTemplate = new TestRestTemplate();


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/" + uri;
    }

    private HttpHeaders getHeaders(String mail, String password) {
        UserLoginDto login = new UserLoginDto();
        login.setEmail(mail);
        login.setPassword(password);

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLogin.getBody().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @BeforeAll
    public void init(){
        UserRegisterDto user = new UserRegisterDto();
        user.setEmail(mail);
        user.setFullName(username);
        user.setPassword(password);

        restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

    }

    @Order(1)
    @Test
    public void testGetUser() {
        final ResponseEntity<UserDto> response = restTemplate.exchange(createURLWithPort("user"), HttpMethod.GET, new HttpEntity<>(getHeaders(mail, password)), UserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mail, response.getBody().getMail());
        assertEquals(username, response.getBody().getUsername());
        assertTrue(response.getBody().getId() > 0);
    }

    @Order(2)
    @Test
    public void testEditUser() {

        UserEditDto dto = new UserEditDto();
        dto.setPassword(password);
        dto.setNewUser(newUser);
        dto.setTheme("dark");
        dto.setNewMail(newMail);

        ResponseEntity<Object> editResponse = restTemplate.exchange(createURLWithPort("user/edit"), HttpMethod.PATCH, new HttpEntity<>(dto, getHeaders(mail, password)), Object.class);
        assertEquals(HttpStatus.OK, editResponse.getStatusCode());

        final ResponseEntity<UserDto> newUserResponse = restTemplate.exchange(createURLWithPort("user"), HttpMethod.GET, new HttpEntity<>(getHeaders(newMail, password)), UserDto.class);
        assertEquals(HttpStatus.OK, newUserResponse.getStatusCode());
        assertEquals(newMail, newUserResponse.getBody().getMail());
        assertEquals(newUser, newUserResponse.getBody().getUsername());
        assertEquals("dark", newUserResponse.getBody().getTheme());
        assertTrue(newUserResponse.getBody().getId() > 0);
    }

    @Order(3)
    @Test
    public void testEditUserPassword() {
        UserEditPasswordDto dto = new UserEditPasswordDto();
        dto.setPassword(newPassword);
        dto.setCurrentPassword(password);
        dto.setPasswordRepeat(newPassword);

        ResponseEntity<Object> editResponse = restTemplate.exchange(createURLWithPort("user/edit/password"), HttpMethod.PATCH, new HttpEntity<>(dto, getHeaders(newMail, password)), Object.class);
        assertEquals(HttpStatus.OK, editResponse.getStatusCode());

        final ResponseEntity<UserDto> newUserResponse = restTemplate.exchange(createURLWithPort("user"), HttpMethod.GET, new HttpEntity<>(getHeaders(newMail, newPassword)), UserDto.class);
        assertEquals(HttpStatus.OK, newUserResponse.getStatusCode());
        assertEquals(newMail, newUserResponse.getBody().getMail());
        assertEquals(newUser, newUserResponse.getBody().getUsername());
        assertTrue(newUserResponse.getBody().getId() > 0);
    }

    @Order(4)
    @Test
    public void testDeleteUser() {
        ResponseEntity<Object> deleteResponse = restTemplate.exchange(createURLWithPort("user"), HttpMethod.DELETE, new HttpEntity<>(getHeaders(newMail, newPassword)), Object.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        UserLoginDto login = new UserLoginDto();
        login.setEmail(newMail);
        login.setPassword(newPassword);

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        assertEquals(HttpStatus.UNAUTHORIZED, userLogin.getStatusCode());
    }
}
