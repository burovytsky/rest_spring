package com.burovytsky.rest_spring.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.burovytsky.rest_spring.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest()
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/person/").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        Person[] people = mapFromJson(result.getResponse().getContentAsString(), Person[].class);
        assertTrue(people.length > 0);
    }

    @Test
    void findByIdTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/person/5").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        Person person = mapFromJson(result.getResponse().getContentAsString(), Person.class);
        assertEquals(person, new Person(5, "login", "password"));
    }

    @Test
    void create() throws Exception {
        Person person = new Person();
        person.setPassword("password");
        person.setLogin("login");
        String toJson = mapToJson(person);
        MvcResult result = this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson))
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
        Person createdPerson = mapFromJson(result.getResponse().getContentAsString(), Person.class);
        mockMvc.perform(delete("/person/" + createdPerson.getId()));
    }

    @Test
    void updateTest() throws Exception {
        Person person = new Person(3, "login", "newPassword");
        String toJson = mapToJson(person);
        MvcResult result = this.mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/person/2")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> tClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, tClass);
    }
}

