package com.example.springaop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.springaop.model.Users;

@Component
public class UserService {

	private static Map<Long, Users> store = new HashMap<Long, Users>();
	static {
		store.put(1L, new Users(1, "Jack", "Smith"));
		store.put(2L, new Users(2, "Adam", "Johnson"));
	}

	public Users getUserById(long id) {
		return store.get(id);
	}

	public Users setUserFirstName(String firstName, long id) {
		Users cust = store.get(id);
		cust.setFirstName(firstName);
		return cust;
	}

	public Users setUserLastName(String lastName, long id) {
		Users cust = store.get(id);
		cust.setLastName(lastName);
		return cust;
	}

	public List<Users> findUserByLastName(String lastName) {
		List<Users> listCust = new ArrayList<>();

		for (Long id : store.keySet()) {
			if (store.get(id).getLastName().equals(lastName)) {
				listCust.add(store.get(id));
			}
		}

		return listCust;
	}

	public List<Users> findAllUsers() {
		List<Users> listCust = new ArrayList<>();

		for (Long id : store.keySet()) {
			listCust.add(store.get(id));
		}

		return listCust;
	}
}
