package com.jukka.vulnweb;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;

//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.jukka.vulnweb.persistence.model.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class VulnwebApplicationLiveTests
{
    private static final String API_ROOT = "http://localhost:8080/api/users";
    
    @Test
    public void whenGetAllUsers_thenOK()
    {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetUsersByname_thenOK()
    {
        final User user = createRandomUser();
        createUserAsUri(user);

        final Response response = RestAssured.get(API_ROOT + "/username/" + user.getName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedUserById_thenOK()
    {
        final User user = createRandomUser();
        final String location = createUserAsUri(user);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(user.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenGetNotExistUserById_thenNotFound()
    {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewUser_thenCreated()
    {
        final User user = createRandomUser();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidUser_thenError() {
        final User user = createRandomUser();
        user.setRole(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedUser_thenUpdated()
    {
        final User user = createRandomUser();
        final String location = createUserAsUri(user);

        user.setId(Long.parseLong(location.split("api/users/")[1]));
        user.setName("newName");
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newName", response.jsonPath()
            .get("name"));
    }

    @Test
    public void whenDeleteCreatedUser_thenOk()
    {
        final User user = createRandomUser();
        final String location = createUserAsUri(user);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    private User createRandomUser()
    {
        final User user = new User(randomAlphabetic(10), randomAlphabetic(15),
        		randomAlphabetic(16));
        return user;
    }

    private String createUserAsUri(User user)
    {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(user)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
            .get("id");
    }
}
