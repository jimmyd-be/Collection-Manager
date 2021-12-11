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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:cm_item;DB_CLOSE_DELAY=-1")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerIT {

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

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        UserRegisterDto user = new UserRegisterDto.Builder()
                .withFullName(username)
                .withPassword(password)
                .withEmail(mail)
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
    public void addItemToCollectionTest() {
        Map<String, String> itemData = new HashMap<>();

        itemData.put("1_0", "Movie title");
        itemData.put("9_0", "http://image-movie.com/test.jpg");
        itemData.put("2_0", "Action");
        itemData.put("2_1", "Fantasy");

        final ResponseEntity<Object> result = restTemplate.postForEntity(createURLWithPort("item/add/collection/1"), new HttpEntity<>(itemData, getHeaders()), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Order(2)
    @Test
    public void getItemByIdTest() {
        Map<String, String> itemData = new HashMap<>();

        itemData.put("1_0", "Movie title");
        itemData.put("9_0", "http://image-movie.com/test.jpg");
        itemData.put("2_0", "Action");
        itemData.put("2_1", "Fantasy");

        final ResponseEntity<ItemDto> result = restTemplate.exchange(createURLWithPort("item/get/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals("Movie title", result.getBody().getName());
        assertEquals("http://image-movie.com/test.jpg", result.getBody().getImage());
        assertEquals(2, result.getBody().getData().size());
    }

    @Order(3)
    @Test
    public void getItemFromCollectionTest() {

        final ResponseEntity<ItemDto[]> result = restTemplate.exchange(createURLWithPort("item/get/collection/1/0/50"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(1, result.getBody().length);
        assertEquals("Movie title", Arrays.stream(result.getBody()).findFirst().get().getName());
        assertEquals("http://image-movie.com/test.jpg", Arrays.stream(result.getBody()).findFirst().get().getImage());
        assertEquals(2, Arrays.stream(result.getBody()).findFirst().get().getData().size());
    }

    @Order(4)
    @Test
    public void editItemTest() {

        Map<String, String> itemData = new HashMap<>();

        itemData.put("1_0", "New Movie title");
        itemData.put("9_0", "http://image-movie.com/new-test.jpg");
        itemData.put("2_0", "Action2");
        itemData.put("2_1", "Fantasy2");

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("item/edit/1/1"), HttpMethod.PATCH, new HttpEntity<>(itemData, getHeaders()), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<ItemDto[]> getResult = restTemplate.exchange(createURLWithPort("item/get/collection/1/0/50"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto[].class);
        assertEquals(HttpStatus.OK, getResult.getStatusCode());

        assertEquals(2, Arrays.stream(getResult.getBody()).findFirst().get().getId());
        assertEquals("New Movie title", Arrays.stream(getResult.getBody()).findFirst().get().getName());
        assertEquals("http://image-movie.com/new-test.jpg", Arrays.stream(getResult.getBody()).findFirst().get().getImage());
        assertEquals(2, Arrays.stream(getResult.getBody()).findFirst().get().getData().size());
        assertTrue(Arrays.stream(getResult.getBody()).map(n -> n.getData()).flatMap(List::stream).filter(n -> n.getValue().equals("Action2")).findFirst().isPresent());
        assertTrue(Arrays.stream(getResult.getBody()).map(n -> n.getData()).flatMap(List::stream).filter(n -> n.getValue().equals("Fantasy2")).findFirst().isPresent());
    }

    @Order(7)
    @Test
    public void deleteItemTest() {
        final ResponseEntity<ItemDto[]> getResult1 = restTemplate.exchange(createURLWithPort("item/get/collection/1/0/50"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto[].class);

        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("item/2/collection/1"), HttpMethod.DELETE, new HttpEntity<>(getHeaders()), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<ItemDto[]> getResult = restTemplate.exchange(createURLWithPort("item/get/collection/1/0/50"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto[].class);
        assertEquals(HttpStatus.OK, getResult.getStatusCode());

        assertEquals(getResult1.getBody().length - 1, getResult.getBody().length);

        final ResponseEntity<ItemDto> getByIdResult = restTemplate.exchange(createURLWithPort("item/get/1"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto.class);
        assertEquals(HttpStatus.NOT_FOUND, getByIdResult.getStatusCode());
    }

    @Order(5)
    @Test
    public void searchImdbMovieTest() {
        final ResponseEntity<ItemSearchDto[]> result = restTemplate.exchange(createURLWithPort("item/external/movies?search=tenet"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemSearchDto[].class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertTrue(result.getBody().length > 0);
    }

    @Order(6)
    @Test
    public void addImdbMovieTest() {
        final ResponseEntity<Object> result = restTemplate.exchange(createURLWithPort("item/external/add/collection/1/imdbMovie/tt0241527"), HttpMethod.POST, new HttpEntity<>(getHeaders()), Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        final ResponseEntity<ItemDto[]> itemListResult = restTemplate.exchange(createURLWithPort("item/get/collection/1/0/50"), HttpMethod.GET, new HttpEntity<>(getHeaders()), ItemDto[].class);
        assertEquals(HttpStatus.OK, itemListResult.getStatusCode());

        assertEquals(2, itemListResult.getBody().length);
        assertEquals("Harry Potter and the Sorcerer's Stone", itemListResult.getBody()[0].getName());


    }
}
