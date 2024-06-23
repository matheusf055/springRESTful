package com.api.springrest.integrationtests.controller;

import com.api.springrest.configs.TestConfigs;
import com.api.springrest.integrationtests.testcontainers.AbstractIntegrationTest;
import com.api.springrest.integrationtests.testcontainers.vo.AccountCredentialsVO;
import com.api.springrest.integrationtests.testcontainers.vo.BookVO;
import com.api.springrest.integrationtests.testcontainers.vo.TokenVO;
import com.api.springrest.integrationtests.testcontainers.wrapper.WrapperBookVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;

    private static BookVO book;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    book = new BookVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException, JsonMappingException {

        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException, JsonMappingException {
        mockBook();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookVO persistedBook = mapper.readValue(content, BookVO.class);
        book = persistedBook;

        assertNotNull(persistedBook);

        assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getTitle());
        assertNotNull(persistedBook.getPrice());
        assertNotNull(persistedBook.getLaunchDate());

        assertTrue(persistedBook.getId() > 0);

        assertEquals("Utopia", persistedBook.getTitle());
        assertEquals("Tomas Moore", persistedBook.getAuthor());
        assertNotNull(persistedBook.getLaunchDate());
        assertEquals(new BigDecimal("20.00"), persistedBook.getPrice());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException, JsonMappingException {
        book.setAuthor("Tomas Moore");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookVO persistedBook = mapper.readValue(content, BookVO.class);
        book = persistedBook;

        assertNotNull(persistedBook);

        assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getTitle());
        assertNotNull(persistedBook.getPrice());
        assertNotNull(persistedBook.getLaunchDate());

        assertEquals(book.getId(), persistedBook.getId());

        assertEquals("Utopia", persistedBook.getTitle());
        assertEquals("Tomas Moore", persistedBook.getAuthor());
        long expectedLaunchDate = book.getLaunchDate().getTime();
        long actualLaunchDate = persistedBook.getLaunchDate().getTime();
        long delta = 1000L;
        assertEquals(new BigDecimal("20.00"), persistedBook.getPrice());

        assertTrue(Math.abs(expectedLaunchDate - actualLaunchDate) < delta,
                "The difference between expected and actual launch date should be less than " + delta);
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException, JsonMappingException {
        mockBook();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookVO persistedBook = mapper.readValue(content, BookVO.class);
        book = persistedBook;

        assertNotNull(persistedBook);

        assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getTitle());
        assertNotNull(persistedBook.getPrice());
        assertNotNull(persistedBook.getLaunchDate());

        assertEquals(book.getId(), persistedBook.getId());

        assertEquals("Utopia", persistedBook.getTitle());
        assertEquals("Tomas Moore", persistedBook.getAuthor());
        assertEquals(new BigDecimal("20.00"), persistedBook.getPrice());

        long expectedLaunchDate = book.getLaunchDate().getTime();
        long actualLaunchDate = persistedBook.getLaunchDate().getTime();
        long delta = 1000L; // 1 second tolerance

        assertTrue(Math.abs(expectedLaunchDate - actualLaunchDate) < delta,
                "The difference between expected and actual launch date should be less than " + delta);
    }

    @Test
    @Order(4)
    public void testDelete() throws JsonProcessingException, JsonMappingException {

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {
        authorization();
        assertNotNull(specification, "Specification must be initialized before use");

        System.out.println("Specification state before testFindAll: " + specification);

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                .queryParams("page", 0 , "limit", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperBookVO wrapper = mapper.readValue(content, WrapperBookVO.class);
        List<BookVO> books = wrapper.getEmbedded().getBooks();

        BookVO foundBookOne = books.get(0);

        assertNotNull(foundBookOne.getId());
        assertNotNull(foundBookOne.getTitle());
        assertNotNull(foundBookOne.getAuthor());
        assertNotNull(foundBookOne.getPrice());
        assertTrue(foundBookOne.getId() > 0);
        assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", foundBookOne.getTitle());
        assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", foundBookOne.getAuthor());
        assertEquals(54.0, foundBookOne.getPrice().doubleValue(), 0.001);

        BookVO foundBookFive = books.get(4);

        assertNotNull(foundBookFive.getId());
        assertNotNull(foundBookFive.getTitle());
        assertNotNull(foundBookFive.getAuthor());
        assertNotNull(foundBookFive.getPrice());
        assertTrue(foundBookFive.getId() > 0);
        assertEquals("Domain Driven Design", foundBookFive.getTitle());
        assertEquals("Eric Evans", foundBookFive.getAuthor());
        assertEquals(92.0, foundBookFive.getPrice().doubleValue(), 0.001);
    }


    @Test
    @Order(6)
    public void testFindAllWithoutToken() throws JsonProcessingException, JsonMappingException {

        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .contentType(TestConfigs.CONTENT_TYPE)
                .when()
                .get()
                .then()
                .statusCode(403);
    }

    private void mockBook() {
        book.setTitle("Utopia");
        book.setAuthor("Tomas Moore");
        book.setLaunchDate(new Date());
        book.setPrice(new BigDecimal("20.00"));
    }
}
