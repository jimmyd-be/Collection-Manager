package be.jimmyd.cm.it;


import be.jimmyd.cm.dto.TokenDto;
import be.jimmyd.cm.dto.UserDto;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:cm_admin;DB_CLOSE_DELAY=-1")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerIT {

    TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;
    private UserRegisterDto admin;
    private UserRegisterDto user;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/" + uri;
    }

    private HttpHeaders getHeaders(UserRegisterDto dto) {
        UserLoginDto login = new UserLoginDto();
        login.setEmail(dto.getEmail());
        login.setPassword(dto.getPassword());

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLogin.getBody().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @BeforeAll
    public void init() {

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        admin = new UserRegisterDto();
        admin.setEmail("admin@test.be");
        admin.setFullName("admin");
        admin.setPassword("Test123456789");

        restTemplate.postForEntity(createURLWithPort("auth/register"), admin, Object.class);

        user = new UserRegisterDto();
        user.setEmail("user@test.be");
        user.setFullName("user");
        user.setPassword("Test123456789");

        restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);

    }

    @Test
    @Order(1)
    public void getAllUsers() {

        final ResponseEntity<UserDto[]> result = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(2, result.getBody().length);
    }

    @Test
    @Order(2)
    public void getAllUsersNoAccess() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(user)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    @Order(3)
    public void getAllSettings() {

        final ResponseEntity<UserDto[]> result = restTemplate.exchange(createURLWithPort("admin/settings"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertTrue(result.getBody() == null || result.getBody().length > 0);
    }

    @Test
    @Order(4)
    public void getAllSettingsNoAccess() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/settings"), HttpMethod.GET, new HttpEntity<>(getHeaders(user)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    @Order(5)
    public void disableOnlyAdminUser() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/disable/1"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    @Order(6)
    public void disableUser() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/disable/2"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<UserDto[]> userResult = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertTrue(
                Arrays.stream(userResult.getBody())
                        .filter(n -> !n.isActive())
                        .filter(n -> n.getId() == 2)
                        .findFirst().isPresent());
    }

    @Test
    @Order(7)
    public void enableUser() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/enable/2"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<UserDto[]> userResult = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertTrue(
                Arrays.stream(userResult.getBody())
                        .filter(n -> n.isActive())
                        .anyMatch(n -> n.getId() == 2));
    }

    @Test
    @Order(8)
    public void changeAdminOnOnlyAdmin() {
        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/set/admin/1"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    @Order(9)
    public void enableAdmin() {
        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/set/admin/2"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<UserDto[]> users = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertEquals(2L, Arrays.stream(users.getBody()).filter(n -> n.isAdmin()).count());
    }

    @Test
    @Order(10)
    public void disableAdmin() {
        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/set/admin/2"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<UserDto[]> users = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertEquals(1L, Arrays.stream(users.getBody()).filter(n -> n.isAdmin()).count());
    }

    @Test
    @Order(11)
    public void deleteLastAdminUser() {
        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/1"), HttpMethod.DELETE, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());

        final ResponseEntity<UserDto[]> users = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertEquals(2L, Arrays.stream(users.getBody()).count());
    }

    @Test
    @Order(12)
    public void deleteUser() {
        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/2"), HttpMethod.DELETE, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<UserDto[]> users = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertEquals(1L, Arrays.stream(users.getBody()).filter(n -> n.isAdmin()).count());
    }
}
