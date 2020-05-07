package com.example.springaop.ui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springaop.controller.MainController;
import com.example.springaop.model.User;
import com.example.springaop.model.UserDB;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import antlr.collections.List;


@Route("statusica")
public class UsersListUI extends HorizontalLayout{
	
	private TextField txtid, txtfirstname, txtlastname, txtphone,txtemail;
	private Button btnAddNew, btnUpdate;
	private ListBox<User> listBox = new ListBox<>();
 
	private ArrayList<User> users = new ArrayList<User>();
	
	@Autowired
	public MainController mainController;

	@Autowired
	public UsersListUI(){
		setupUsersList();
		
		add(listBox, createForm());
	}
	
	private void setupUsersList() {
		users.clear();
		
		DataProvider<User, Void> dataProvider =
	    DataProvider.fromCallbacks(
	        // First callback fetches items based on a query
	        query -> {
	            // The index of the first item to load
	            int offset = query.getOffset();

	            // The number of items to load
	            int limit = query.getLimit();
	            
	            ArrayList<User> usersTemp = new ArrayList<User>();
				mainController.getAllUsers().forEach(user -> {
					usersTemp.add(new User(user.getId().toString(), user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail()));
				});            		

	            return usersTemp.stream();
	        }, 
	        // Second callback fetches the number of items
	        // for a query
	        query -> 30
        );
		
		
		listBox.addAttachListener(listener -> {
			listBox.setDataProvider(dataProvider);
		});
		
		listBox.setItems(users);
		listBox.setRenderer(new ComponentRenderer<>(user -> {

			Label id = new Label("Id: " + user.getId());
			Label name = new Label("Name: " + user.getFullName());
			Label phone = new Label("Phone: " + user.getPhone());
			Label email = new Label("Email: " + user.getEmail());

			NativeButton button = new NativeButton("Archive", event -> {
				user.isArchived = true;
				clearFormData();
				listBox.getDataProvider().refreshItem(user);
			});

			Div labels = new Div(id, name, phone, email);
			Div layout = new Div(labels, button);
			labels.getStyle().set("display", "flex")
			.set("flexDirection", "column")
			.set("marginRight", "10px");
			layout.getStyle().set("display", "flex")
			.set("alignItems", "center");
			return layout;
		}));

		listBox.setItemEnabledProvider(item -> {
			Boolean isArchived = false;
			for(User user : users) {
				if(user.getId() == item.getId()) {
					isArchived = user.isArchived;
				}
			}

			return !isArchived;
		});

		listBox.addValueChangeListener(event -> {
			populateForm(listBox.getValue());
			
			btnUpdate.setVisible(true);
		});
	}

	private FormLayout createForm(){
		FormLayout f = new FormLayout();

		txtid = new TextField("Id");
		txtfirstname = new TextField("First Name");
		txtlastname = new TextField("Last Name");
		txtphone = new TextField("Phone");
		txtemail = new TextField("Email");

		btnAddNew = new Button("Add New",
				VaadinIcon.PLUS.create());
		btnAddNew.addClickListener(event->save());

		btnUpdate = new Button("Update User",
				VaadinIcon.ARROW_CIRCLE_UP.create());
		btnUpdate.addClickListener(event->updateUser());
		btnUpdate.setVisible(false);

		f.add(txtid, txtfirstname,txtlastname,txtemail,txtphone, btnAddNew, btnUpdate);
		return f;
	}

	private void updateUser() {
		Integer id = Integer.parseInt(txtid.getValue().trim());
		String firstName = txtfirstname.getValue().trim();
		String lastName = txtlastname.getValue().trim();
		String email = txtemail.getValue().trim();
		String phone = txtphone.getValue().trim();

		if (txtid.getValue().trim().isEmpty() || firstName.isEmpty() || lastName.isEmpty()
				|| email.isEmpty() || phone.isEmpty()) {
			Notification.show("Input data cannot be empty");
			return;
		}

		Boolean userUpdated = false;
		for(User user : users) {
			if(user.getId().equals(txtid.getValue().trim())) {

				user.setId(txtid.getValue().trim());
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setPhone(phone);

				userUpdated = true;

				listBox.getDataProvider().refreshItem(user);
				break;
			}
		}

		if(userUpdated) {
			mainController.addNewUser(new UserDB(id, firstName, lastName, email, phone));
			clearFormData();
			Notification.show("User has been successfully updated");
		} else {
			Notification.show("User not found!");
		}
		
		btnUpdate.setVisible(false);
	}

	private void save(){
		Integer id = Integer.parseInt(txtid.getValue().trim());
		String firstName = txtfirstname.getValue().trim();
		String lastName = txtlastname.getValue().trim();
		String email = txtemail.getValue().trim();
		String phone = txtphone.getValue().trim();

		if (txtid.getValue().trim().isEmpty() || firstName.isEmpty() || lastName.isEmpty()
				|| email.isEmpty() || phone.isEmpty()) {
			Notification.show("Input data cannot be empty");
			return;
		}

		Boolean userFound = false;
		for(User user : users) {
			if(user.getId().equals(txtid.getValue().trim())) {
				userFound = true;
			}
		}

		if (userFound == false) {
			users.add(new User(txtid.getValue().trim(), firstName, lastName, phone, email));

			listBox.setItems(users);
			mainController.addNewUser(new UserDB(id, firstName, lastName, email, phone));
		}else{
			Notification.show("Cannot save. Same name exists. Try different name.");
		}
		
		clearFormData();
	}

	public void clearFormData(){
		txtid.setValue("");
		txtfirstname.setValue("");
		txtlastname.setValue("");
		txtphone.setValue("");
		txtemail.setValue("");
	}

	public void populateForm(User user){
		txtid.setValue(user.getId());
		txtfirstname.setValue(user.getFirstName());
		txtlastname.setValue(user.getLastName());
		txtemail.setValue(user.getEmail());
		txtphone.setValue(user.getPhone());
	}
}
