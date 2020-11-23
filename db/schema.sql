create table person (
   id serial primary key not null,
   login varchar(2000),
   password varchar(2000)
);

create table employee(
    id serial primary key not null,
    name varchar(200),
    surname varchar(200),
    inn varchar(200),
    hiring_date timestamp  default now()
);

create table employee_person(
    employee_id int references employee(id),
    person_id int references person(id)
)