package com.burovytsky.rest_spring.controller;

import com.burovytsky.rest_spring.domain.Employee;
import com.burovytsky.rest_spring.domain.Person;
import com.burovytsky.rest_spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeRepository repository;
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";
    @Autowired
    private RestTemplate rest;

    public EmployeeController(final EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(
                this.repository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var employee = repository.findById(id);
        return new ResponseEntity<>(
                employee.orElse(new Employee()),
                employee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                this.repository.save(employee),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var employee = repository.findById(id);
        employee.ifPresent(repository::delete);
        return employee.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/addAccount/{id}")
    public ResponseEntity<Person> createAccount(@RequestBody Person person, @PathVariable int id) {
        var employee = repository.findById(id);
        employee.ifPresent(value -> {
            value.getAccounts().add(person);
            repository.save(value);
        });
        return new ResponseEntity<>(
                employee.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<Void> updateAccount(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Void> deleteAccount(@RequestBody Person person, @PathVariable int id) {
        var employee = repository.findById(id);
        employee.ifPresent(value -> {
            value.getAccounts().remove(person);
            repository.save(value);
            rest.delete(API_ID, person.getId());
        });
        return employee.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
//curl -H 'Content-Type: application/json' -X POST -d '{"id":"1","name":"im","surname":"sur","inn":"2343","accounts":'{"id":"13","login":"job4j@gmail.com","password":"123"}'}' http://localhost:8080/employee/