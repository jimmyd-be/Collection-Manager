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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:cm_col;DB_CLOSE_DELAY=-1")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CollectionControllerIT {

    private final String mail = "test@test.be";
    private final String password = "Testpassword12345789";
    private final String username = "Test user";
    private final String mailSecondUser = "test2@test.be";
    private final String passwordSecondUser = "Testpassword12345789";
    private final String usernameSecondUser = "Test user 2";
    TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/" + uri;
    }

    private HttpHeaders getHeaders() {
        UserLoginDto login = new UserLoginDto();
        login.setEmail(mail);
        login.setPassword(password);

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLogin.getBody().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders getHeadersUser2() {
        UserLoginDto login = new UserLoginDto();
        login.setEmail(mailSecondUser);
        login.setPassword(passwordSecondUser);

        final ResponseEntity<TokenDto> userLogin = restTemplate.postForEntity(createURLWithPort("auth/login"), login, TokenDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userLogin.getBody().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @BeforeAll
    public void init() {

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        UserRegisterDto user = new UserRegisterDto();
        user.setEmail(mail);
        user.setFullName(username);
        user.setPassword(password);

        UserRegisterDto user2 = new UserRegisterDto();
        user2.setEmail(mailSecondUser);
        user2.setFullName(usernameSecondUser);
        user2.setPassword(passwordSecondUser);

        restTemplate.postForEntity(createURLWithPort("auth/register"), user, Object.class);
        restTemplate.postForEntity(createURLWithPort("auth/register"), user2, Object.class);
    }

    @Test
    @Order(1)
    public void addCollectionTest() {
        CollectionDto collectionDto = new CollectionDto();
        collectionDto.setName("Movie collection");
        collectionDto.setType("Movies");

        FieldDto field = new FieldDto();
        field.setName("custom field");
        field.setLabel("Field label");
        field.setPlaceholder("Placeholder");
        field.setType("text");
        field.setWidget("default");
        field.setPlace("main");

        List<FieldDto> fieldDtoList = new ArrayList<>();
        fieldDtoList.add(field);
        collectionDto.setFields(fieldDtoList);

        final ResponseEntity<Object> response = restTemplate.postForEntity(createURLWithPort("collection/add"), new HttpEntity<>(collectionDto, getHeaders()), Object.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void getByUserTest() {
        final ResponseEntity<CollectionDto[]> response = restTemplate.exchange(createURLWithPort("collection/user"), HttpMethod.GET, new HttpEntity<>(getHeaders()), CollectionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(Arrays.stream(response.getBody()).findFirst().isPresent());
        assertTrue(Arrays.stream(response.getBody()).findFirst().get().getId() > 0);
        assertEquals("Movie collection", Arrays.stream(response.getBody()).findFirst().get().getName());
        assertEquals("Movies", Arrays.stream(response.getBody()).findFirst().get().getType());
        assertEquals(1, Arrays.stream(response.getBody()).findFirst().get().getFields().size());
    }

    @Test
    @Order(3)
    public void getByIdTest() {
        final ResponseEntity<CollectionDto> response = restTemplate.exchange(createURLWithPort("collection/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), CollectionDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1, response.getBody().getId());
        assertEquals("Movie collection", response.getBody().getName());
        assertEquals("Movies", response.getBody().getType());
        assertEquals(1, response.getBody().getFields().size());
    }

    @Test
    @Order(4)
    public void getCollectionTypesTest() {
        final ResponseEntity<String[]> response = restTemplate.exchange(createURLWithPort("collection/types"), HttpMethod.GET, new HttpEntity<>(getHeaders()), String[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(Arrays.asList(response.getBody()).contains("Movies"));
        assertTrue(Arrays.asList(response.getBody()).contains("Games"));
        assertTrue(Arrays.asList(response.getBody()).contains("Magazines"));
        assertTrue(Arrays.asList(response.getBody()).contains("Books"));
        assertTrue(Arrays.asList(response.getBody()).contains("Disks"));
        assertTrue(Arrays.asList(response.getBody()).contains("Comics"));
    }

    @Test
    @Order(4)
    public void getAllCollectionUsersTest() {
        final ResponseEntity<UserCollectionDto[]> response = restTemplate.exchange(createURLWithPort("collection/1/users"), HttpMethod.GET, new HttpEntity<>(getHeaders()), UserCollectionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(username, Arrays.asList(response.getBody()).get(0).getUserName());
        assertEquals("Admin", Arrays.asList(response.getBody()).get(0).getRoleName());
    }

    @Test
    @Order(5)
    public void shareTest() {
        CollectionShareDto shareDto = new CollectionShareDto();
        shareDto.setRole("Editor");
        shareDto.setUserName(usernameSecondUser);

        final ResponseEntity response = restTemplate.exchange(createURLWithPort("collection/1/share"), HttpMethod.POST, new HttpEntity<>(shareDto, getHeaders()), Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final ResponseEntity<UserCollectionDto[]> responseUsers = restTemplate.exchange(createURLWithPort("collection/1/users"), HttpMethod.GET, new HttpEntity<>(getHeaders()), UserCollectionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(2, responseUsers.getBody().length);

        final UserCollectionDto user1 = Arrays.asList(responseUsers.getBody()).stream().filter(n -> n.getUserId() == 1).findFirst().get();
        final UserCollectionDto user2 = Arrays.asList(responseUsers.getBody()).stream().filter(n -> n.getUserId() == 2).findFirst().get();

        assertEquals(username, user1.getUserName());
        assertEquals(usernameSecondUser, user2.getUserName());
        assertEquals("Admin", user1.getRoleName());
        assertEquals("Editor", user2.getRoleName());

        //Check that user 2 has collection in his list
        final ResponseEntity<CollectionDto[]> collectionsUser2 = restTemplate.exchange(createURLWithPort("collection/user"), HttpMethod.GET, new HttpEntity<>(getHeadersUser2()), CollectionDto[].class);
        assertEquals(HttpStatus.OK, collectionsUser2.getStatusCode());

        assertTrue(Arrays.stream(collectionsUser2.getBody()).findFirst().isPresent());
        assertTrue(Arrays.stream(collectionsUser2.getBody()).findFirst().get().getId() > 0);
        assertEquals("Movie collection", Arrays.stream(collectionsUser2.getBody()).findFirst().get().getName());
        assertEquals("Movies", Arrays.stream(collectionsUser2.getBody()).findFirst().get().getType());
        assertEquals(1, Arrays.stream(collectionsUser2.getBody()).findFirst().get().getFields().size());
    }

    @Test
    @Order(6)
    public void deleteUserFromCollectionTest() {
        final ResponseEntity response = restTemplate.exchange(createURLWithPort("collection/1/user/2"), HttpMethod.DELETE, new HttpEntity<>(getHeaders()), Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //Check that user 2 has no collection in his list
        final ResponseEntity<CollectionDto[]> collectionsUser2 = restTemplate.exchange(createURLWithPort("collection/user"), HttpMethod.GET, new HttpEntity<>(getHeadersUser2()), CollectionDto[].class);
        assertEquals(HttpStatus.OK, collectionsUser2.getStatusCode());

        assertFalse(Arrays.stream(collectionsUser2.getBody()).findFirst().isPresent());
    }

    @Test
    @Order(7)
    public void editCollectionTest() {
        CollectionDto collectionDto = new CollectionDto();
        collectionDto.setId(1);
        collectionDto.setName("Edited Movie collection");
        collectionDto.setType("Movies");

        FieldDto field = new FieldDto();
        field.setId(48);
        field.setName("Edited custom field");
        field.setLabel("Field label");
        field.setPlaceholder("Placeholder");
        field.setType("text");
        field.setWidget("default");
        field.setPlace("main");

        FieldDto field2 = new FieldDto();
        field2.setName("New custom field");
        field2.setLabel("new Field label");
        field2.setPlaceholder("Placeholder");
        field2.setType("text");
        field2.setWidget("default");
        field2.setPlace("main");

        List<FieldDto> fieldDtoList = new ArrayList<>();
        fieldDtoList.add(field);
        fieldDtoList.add(field2);
        collectionDto.setFields(fieldDtoList);

        final ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort("collection/edit"), HttpMethod.PATCH, new HttpEntity<>(collectionDto, getHeaders()), Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final ResponseEntity<CollectionDto> checkResponse = restTemplate.exchange(createURLWithPort("collection/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), CollectionDto.class);
        assertEquals(HttpStatus.OK, checkResponse.getStatusCode());

        assertEquals(1, checkResponse.getBody().getId());
        assertEquals("Edited Movie collection", checkResponse.getBody().getName());
        assertEquals("Movies", checkResponse.getBody().getType());
        assertEquals(2, checkResponse.getBody().getFields().size());
    }

    @Test
    @Order(8)
    public void deleteCollectionTest() {
        final ResponseEntity response = restTemplate.exchange(createURLWithPort("collection/1/"), HttpMethod.DELETE, new HttpEntity<>(getHeaders()), Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final ResponseEntity<CollectionDto[]> collectionsUser = restTemplate.exchange(createURLWithPort("collection/user"), HttpMethod.GET, new HttpEntity<>(getHeaders()), CollectionDto[].class);
        assertEquals(HttpStatus.OK, collectionsUser.getStatusCode());

        assertFalse(Arrays.stream(collectionsUser.getBody()).findFirst().isPresent());
    }


}
