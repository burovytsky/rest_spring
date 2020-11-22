package com.burovytsky.rest_spring.repository;

import com.burovytsky.rest_spring.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
