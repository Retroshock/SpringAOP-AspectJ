package com.example.springaop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.springaop.aspect.Loggable;
import com.example.springaop.model.Users;

@Component
public class UserLoggableService {

	private static Map<Long, Users> store = new HashMap<Long, Users>();
	static {
		store.put(1L, new Users(1, "Jack", "Smith"));
		store.put(2L, new Users(2, "Adam", "Johnson"));
	}

	@Loggable
	public Users getCustomerById(long id) {
		return store.get(id);
	}

	public Users setCustomerFirstName(String firstName, long id) {
		Users cust = store.get(id);
		cust.setFirstName(firstName);
		return cust;
	}

	public Users setCustomerLastName(String lastName, long id) {
		Users cust = store.get(id);
		cust.setLastName(lastName);
		return cust;
	}

	public List<Users> findCustomerByLastName(String lastName) {
		List<Users> listCust = new ArrayList<>();

		for (Long id : store.keySet()) {
			if (store.get(id).getLastName().equals(lastName)) {
				listCust.add(store.get(id));
			}
		}

		return listCust;
	}

	public List<Users> findAllCustomers() {
		List<Users> listCust = new ArrayList<>();

		for (Long id : store.keySet()) {
			listCust.add(store.get(id));
		}

		return listCust;
	}
}
