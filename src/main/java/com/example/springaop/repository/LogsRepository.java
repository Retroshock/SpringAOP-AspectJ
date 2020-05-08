package com.example.springaop.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springaop.model.Logs;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

public interface LogsRepository extends CrudRepository<Logs, Integer> {

}
