package com.example.springaop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.springaop.model.User;

@Component
public class UserService {

	private static Map<String, User> store = new HashMap<String, User>();
	static {
		store.put("1", new User("1", "Jack", "Smith"));
		store.put("2", new User("2", "Adam", "Johnson"));
	}

	public User getUserById(String id) {
		return store.get(id);
	}

	public User setUserFirstName(String firstName, String id) {
		User cust = store.get(id);
		cust.setFirstName(firstName);
		return cust;
	}

	public User setUserLastName(String lastName, String id) {
		User cust = store.get(id);
		cust.setLastName(lastName);
		return cust;
	}

	public List<User> findUserByLastName(String lastName) {
		List<User> listCust = new ArrayList<>();

		for (String id : store.keySet()) {
			if (store.get(id).getLastName().equals(lastName)) {
				listCust.add(store.get(id));
			}
		}

		return listCust;
	}

	public List<User> findAllUsers() {
		List<User> listCust = new ArrayList<>();

		for (String id : store.keySet()) {
			listCust.add(store.get(id));
		}

		return listCust;
	}
}
