package com.example.springaop.ui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springaop.controller.MainController;
import com.example.springaop.model.Maintenance;
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

@Route("info")
public class UsersInfoUI extends HorizontalLayout {

	private TextField txtid, txtsum, txtmonth, txtyear;
	private Button btnUpdate;
	private ListBox<UserDB> listBox = new ListBox<>();

	private ArrayList<UserDB> users = new ArrayList<UserDB>();

	private Maintenance selectedMaintenance;

	@Autowired
	public MainController mainController;

	@Autowired
	public UsersInfoUI() {
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

			Div labels = new Div(id, name, phone, email);

			Div layout = new Div(labels);
			labels.getStyle().set("display", "flex").set("flexDirection", "column").set("marginRight", "10px");
			layout.getStyle().set("display", "flex").set("alignItems", "center");
			return layout;
		}));

		listBox.setItemEnabledProvider(item -> {
			return true;
		});

		listBox.addValueChangeListener(event -> {
			populateForm(listBox.getValue(), mainController.getAllMaintenance());

			btnUpdate.setVisible(true);
		});
	}

	private FormLayout createForm() {
		FormLayout f = new FormLayout();

		txtid = new TextField("User ID");
		txtsum = new TextField("Sum");
		txtmonth = new TextField("Month");
		txtyear = new TextField("Year");

		txtid.setEnabled(false);

		btnUpdate = new Button("Update maintenance", VaadinIcon.ARROW_CIRCLE_UP.create());
		btnUpdate.addClickListener(event -> updateMaintenance());
		btnUpdate.setVisible(false);

		f.add(txtid, txtsum, txtmonth, txtyear, btnUpdate);
		return f;
	}

	private void updateMaintenance() {
		String id = txtid.getValue().trim();
		String sum = txtsum.getValue().trim();
		String month = txtmonth.getValue().trim();
		String year = txtyear.getValue().trim();

		if (id.isEmpty() || sum.isEmpty() || month.isEmpty() || year.isEmpty()) {
			Notification.show("Input data cannot be empty");
			return;
		}


		btnUpdate.setVisible(false);

		if (selectedMaintenance == null) {
			selectedMaintenance = new Maintenance();
		}

		selectedMaintenance.setUserId(Integer.parseInt(id));
		selectedMaintenance.setSum(Double.parseDouble(sum));
		selectedMaintenance.setMonth(Integer.parseInt(month));
		selectedMaintenance.setYear(Integer.parseInt(year));

		mainController.addNewMaintenance(selectedMaintenance);
		Notification.show("Maintenance has been successfully updated");
		
		clearFormData();
	}

	public void clearFormData() {
		txtid.setValue("");
		txtsum.setValue("");
		txtmonth.setValue("");
		txtyear.setValue("");
	}

	public void populateForm(UserDB user, Iterable<Maintenance> allMaintanances) {
		clearFormData();
		txtid.setValue(user.getId().toString());

		allMaintanances.forEach(maintenance -> {
			if(maintenance.getUserId() == user.getId()) {
				txtsum.setValue(maintenance.getSum().toString());
				txtmonth.setValue(maintenance.getMonth().toString());
				txtyear.setValue(maintenance.getYear().toString());

				selectedMaintenance = maintenance;
			}
		});
	}

	private DataProvider<UserDB, Void> dataProvider = DataProvider.fromCallbacks(
			// First callback fetches items based on a query
			query -> {
				// The index of the first item to load
				int offset = query.getOffset();

				// The number of items to load
				int limit = query.getLimit();

				users.clear();
				mainController.getAllUsers().forEach(user -> {
					users.add(new UserDB(user.getId(), user.getFirstName(), user.getLastName(), user.getPhone(),
							user.getEmail()));
				});

				return users.stream();
			},
			// Second callback fetches the number of items
			// for a query
			query -> 30);
}
