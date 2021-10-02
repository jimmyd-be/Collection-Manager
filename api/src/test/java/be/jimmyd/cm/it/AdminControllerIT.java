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
public class AdminControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

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
    public void getAllUsers() {

        final ResponseEntity<UserDto[]> result = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(2, result.getBody().length);
    }

    @Test
    public void getAllUsersNoAccess() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(user)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void getAllSettings() {

        final ResponseEntity<UserDto[]> result = restTemplate.exchange(createURLWithPort("admin/settings"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertTrue(result.getBody() == null || result.getBody().length > 0);
    }

    @Test
    public void getAllSettingsNoAccess() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/settings"), HttpMethod.GET, new HttpEntity<>(getHeaders(user)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void disableOnlyAdminUser() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/disable/1"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
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
    public void enableUser() {

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("admin/user/enable/2"), HttpMethod.PATCH, new HttpEntity<>(getHeaders(admin)), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<UserDto[]> userResult = restTemplate.exchange(createURLWithPort("admin/users"), HttpMethod.GET, new HttpEntity<>(getHeaders(admin)), UserDto[].class);

        assertTrue(
                Arrays.stream(userResult.getBody())
                        .filter(n -> n.isActive())
                        .anyMatch(n -> n.getId() == 2));
    }

}
