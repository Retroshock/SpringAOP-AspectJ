package com.example.springaop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.springaop.aspect.Loggable;
import com.example.springaop.model.UserDB;

@Component
public class UserLoggableService {

	private static Map<String, UserDB> store = new HashMap<String, UserDB>();
	static {
		store.put("1", new UserDB(1, "Jack", "Smith", "", ""));
		store.put("2", new UserDB(2, "Adam", "Johnson", "", ""));
	}

	@Loggable
	public UserDB getCustomerById(String id) {
		return store.get(id);
	}

	public UserDB setCustomerFirstName(String firstName, String id) {
		UserDB cust = store.get(id);
		cust.setFirstName(firstName);
		return cust;
	}

	public UserDB setCustomerLastName(String lastName, String id) {
		UserDB cust = store.get(id);
		cust.setLastName(lastName);
		return cust;
	}

	public List<UserDB> findCustomerByLastName(String lastName) {
		List<UserDB> listCust = new ArrayList<>();

		for (String id : store.keySet()) {
			if (store.get(id).getLastName().equals(lastName)) {
				listCust.add(store.get(id));
			}
		}

		return listCust;
	}

	public List<UserDB> findAllCustomers() {
		List<UserDB> listCust = new ArrayList<>();

		for (String id : store.keySet()) {
			listCust.add(store.get(id));
		}

		return listCust;
	}
}
