package com.burovytsky.rest_spring.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.burovytsky.rest_spring.domain.Person;
import com.burovytsky.rest_spring.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository repository;

    @Test
    void findAllTest() throws Exception {
        List<Person> persons = List.of(new Person(1, "log", "1234"));
        when(repository.findAll()).thenReturn(persons);
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"login\":\"log\",\"password\":\"1234\"}]"));
    }

    @Test
    void findByIdTest() throws Exception {
        Optional<Person> optionalPerson = Optional.of(new Person(1, "log", "1234"));
        when(repository.findById(1)).thenReturn(optionalPerson);
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"login\":\"log\",\"password\":\"1234\"}"));
    }

    @Test
    void createTest() throws Exception {
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"log\",\"password\":\"1234\"}"))
                .andExpect(status().isCreated());
        verify(repository).save(any());
    }

    @Test
    void updateTest() throws Exception {
        mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"login\":\"log\",\"password\":\"1234\"}"))
                .andExpect(status().isOk());
        verify(repository).save(any());
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(request("DELETE", URI.create("/person/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"login\":\"log\",\"password\":\"1234\"}"))
                .andExpect(status().isOk());
        verify(repository).delete(any());
    }
}
//
//
//    protected String mapToJson(Object obj) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(obj);
//    }
//
//    protected <T> T mapFromJson(String json, Class<T> tClass)
//            throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, tClass);
//    }
//    protected String mapToJson(Object obj) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(obj);
//    }
//
//    protected <T> T mapFromJson(String json, Class<T> tClass)
//            throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, tClass);
//    }
//
//    @Test
//    void findAllTest() throws Exception {
//        MvcResult result = this.mockMvc.perform(get("/person/").accept(MediaType.APPLICATION_JSON_VALUE))
//                .andReturn();
//        assertEquals(200, result.getResponse().getStatus());
//        Person[] people = mapFromJson(result.getResponse().getContentAsString(), Person[].class);
//        assertTrue(people.length > 0);
//    }
//
//    @Test
//    void findByIdTest() throws Exception {
//        MvcResult result = this.mockMvc.perform(get("/person/1").accept(MediaType.APPLICATION_JSON_VALUE))
//                .andReturn();
//        assertEquals(200, result.getResponse().getStatus());
//        Person person = mapFromJson(result.getResponse().getContentAsString(), Person.class);
//        assertThat(person, is(new Person(1, "parsentev", "123")));
//    }
//
//    @Test
//    void create() throws Exception {
//        Person person = new Person(6, "login", "password");
//        String toJson = mapToJson(person);
//        MvcResult result = this.mockMvc.perform(post("/person/")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(toJson))
//                .andReturn();
//        assertEquals(201, result.getResponse().getStatus());
//    }
