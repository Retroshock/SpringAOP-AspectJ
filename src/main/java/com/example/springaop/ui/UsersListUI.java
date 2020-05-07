package com.example.springaop.ui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springaop.controller.MainController;
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

@Route("status")
public class UsersListUI extends HorizontalLayout{

	private TextField txtfirstname, txtlastname, txtphone,txtemail;
	private Button btnAddNew, btnUpdate;
	private ListBox<UserDB> listBox = new ListBox<>();

	private ArrayList<UserDB> users = new ArrayList<UserDB>();

	@Autowired
	public MainController mainController;

	@Autowired
	public UsersListUI(){
		setupUsersList();

		add(listBox, createForm());
	}

	private void setupUsersList() {
		listBox.addAttachListener(listener -> {
			listBox.setDataProvider(dataProvider);
		});
		
		listBox.setItems(users);
		listBox.setRenderer(new ComponentRenderer<>(user -> {

			Label id = new Label("Id: " + user.getId());
			Label name = new Label("Name: " + user.getFullName());
			Label phone = new Label("Phone: " + user.getPhone());
			Label email = new Label("Email: " + user.getEmail());

			Button delete = new Button(VaadinIcon.TRASH.create());
			delete.addClickListener(event -> {
				clearFormData();
				mainController.deleteUser(user);
			});
			
			Button info = new Button(VaadinIcon.INFO_CIRCLE.create());
			info.addClickListener(listener -> {
				info.getUI().ifPresent( ui -> ui.navigate("info"));
			});

			Div labels = new Div(id, name, phone, email);
			Div buttons = new Div(delete, info);
			Div layout = new Div(labels, buttons);
			
			labels.getStyle().set("display", "flex")
			.set("flexDirection", "column")
			.set("marginRight", "10px");
			layout.getStyle().set("display", "flex")
			.set("alignItems", "center");
			
			buttons.getStyle().set("display", "flex")
			.set("flexDirection", "column")
			.set("marginRight", "10px");
			buttons.getStyle().set("display", "flex")
			.set("alignItems", "center");
			
			delete.getStyle().set("marginRight", "10px");
			
			return layout;
		}));

		listBox.setItemEnabledProvider(item -> {
			return true;
		});

		listBox.addValueChangeListener(event -> {
			populateForm(listBox.getValue());

			btnUpdate.setVisible(true);
		});
	}

	private FormLayout createForm(){
		FormLayout f = new FormLayout();

		txtfirstname = new TextField("First Name");
		txtlastname = new TextField("Last Name");
		txtphone = new TextField("Phone");
		txtemail = new TextField("Email");

		btnAddNew = new Button("Add New",
				VaadinIcon.PLUS.create());
		btnAddNew.addClickListener(event->save());

		btnUpdate = new Button("Update UserDB",
				VaadinIcon.ARROW_CIRCLE_UP.create());
		btnUpdate.addClickListener(event->updateUser());
		btnUpdate.setVisible(false);

		f.add(txtfirstname,txtlastname,txtemail,txtphone, btnAddNew, btnUpdate);
		return f;
	}

	private void updateUser() {
		String firstName = txtfirstname.getValue().trim();
		String lastName = txtlastname.getValue().trim();
		String email = txtemail.getValue().trim();
		String phone = txtphone.getValue().trim();

		if (firstName.isEmpty() || lastName.isEmpty()
				|| email.isEmpty() || phone.isEmpty()) {
			Notification.show("Input data cannot be empty");
			return;
		}

		UserDB userFound = null;
		
		for(UserDB user : users) {
			if(user.getEmail().equals(email)) {
				userFound = user;
				break;
			}
		}

		btnUpdate.setVisible(false);
		
		if(userFound != null) {
			clearFormData();
			mainController.addNewUser(userFound);
			dataProvider.refreshItem(userFound);
			Notification.show("User has been successfully updated");
		} else {
			Notification.show("User not found!");
		}
	}

	private void save(){
		String firstName = txtfirstname.getValue().trim();
		String lastName = txtlastname.getValue().trim();
		String email = txtemail.getValue().trim();
		String phone = txtphone.getValue().trim();

		if (firstName.isEmpty() || lastName.isEmpty()
				|| email.isEmpty() || phone.isEmpty()) {
			Notification.show("Input data cannot be empty");
			return;
		}

		Boolean userFound = false;
		for(UserDB user : users) {
			if(user.getEmail().equals(email)) {
				userFound = true;
			}
		}

		if (userFound == false) {
			clearFormData();
			mainController.addNewUser(new UserDB(firstName, lastName, email, phone));
			dataProvider.refreshAll();
		}else{
			Notification.show("Cannot save. Same email exists. Try different email.");
		}
	}

	public void clearFormData(){
		txtfirstname.setValue("");
		txtlastname.setValue("");
		txtphone.setValue("");
		txtemail.setValue("");
	}

	public void populateForm(UserDB user){
		txtfirstname.setValue(user.getFirstName());
		txtlastname.setValue(user.getLastName());
		txtemail.setValue(user.getEmail());
		txtphone.setValue(user.getPhone());
	}
	
	private DataProvider<UserDB, Void> dataProvider =
			DataProvider.fromCallbacks(
					// First callback fetches items based on a query
					query -> {
						// The index of the first item to load
						int offset = query.getOffset();

						// The number of items to load
						int limit = query.getLimit();
						
						users.clear();
						mainController.getAllUsers().forEach(user -> {
							users.add(new UserDB(user.getId(), user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail()));
						});            		

						return users.stream();
					}, 
					// Second callback fetches the number of items
					// for a query
					query -> 30
					);
}
