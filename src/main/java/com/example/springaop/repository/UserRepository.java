package com.example.springaop.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springaop.model.UserDB;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<UserDB, Integer> {

}
