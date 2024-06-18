package com.api.springrest.integrationtests.controller;

import com.api.springrest.configs.TestConfigs;
import com.api.springrest.integrationtests.testcontainers.AbstractIntegrationTest;
import com.api.springrest.integrationtests.testcontainers.vo.PersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVO();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_MATHEUS)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                        .body(person)
                    .when()
                        .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;

        Assertions.assertTrue(createdPerson.getId() > 0);
        Assertions.assertNotNull(createdPerson);
        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertNotNull(createdPerson.getAddress());
        Assertions.assertNotNull(createdPerson.getGender());
        Assertions.assertNotNull(createdPerson.getFirstName());
        Assertions.assertNotNull(createdPerson.getLastName());

        Assertions.assertEquals("Richard", createdPerson.getFirstName());
        Assertions.assertEquals("Stallman", createdPerson.getLastName());
        Assertions.assertEquals("New York",createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FERNANDES)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(403)
                        .extract()
                            .body()
                                .asString();

        Assertions.assertNotNull(content);
        Assertions.assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_MATHEUS)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                    .pathParam("id", person.getId())
                    .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;

        Assertions.assertTrue(createdPerson.getId() > 0);
        Assertions.assertNotNull(createdPerson);
        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertNotNull(createdPerson.getAddress());
        Assertions.assertNotNull(createdPerson.getGender());
        Assertions.assertNotNull(createdPerson.getFirstName());
        Assertions.assertNotNull(createdPerson.getLastName());

        Assertions.assertEquals("Richard", createdPerson.getFirstName());
        Assertions.assertEquals("Stallman", createdPerson.getLastName());
        Assertions.assertEquals("New York",createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(4)
    public void testFindByIdWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FERNANDES)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE)
                    .pathParam("id", person.getId())
                    .when()
                    .get("{id}")
                .then()
                    .statusCode(403)
                        .extract()
                        .body()
                         .asString();

        Assertions.assertNotNull(content);
        Assertions.assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New York");
        person.setGender("Male");
    }
}
