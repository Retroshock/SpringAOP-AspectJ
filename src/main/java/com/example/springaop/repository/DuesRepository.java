package com.example.springaop.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springaop.model.Dues;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DuesRepository extends CrudRepository<Dues, Integer> {

}
