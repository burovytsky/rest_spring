package com.burovytsky.rest_spring.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String inn;
    private Timestamp hiringDate;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_person",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> accounts;

    public static Employee of(int id, String name, String surname, String inn) {
        Employee employee = new Employee();
        employee.id = id;
        employee.name = name;
        employee.surname = surname;
        employee.inn = inn;
        employee.hiringDate = new Timestamp(System.currentTimeMillis());
        employee.accounts = new HashSet<>();
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Timestamp getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Timestamp hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Set<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Person> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(name, employee.name) &&
                Objects.equals(surname, employee.surname) &&
                Objects.equals(inn, employee.inn) &&
                Objects.equals(hiringDate, employee.hiringDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, inn, hiringDate);
    }
}
