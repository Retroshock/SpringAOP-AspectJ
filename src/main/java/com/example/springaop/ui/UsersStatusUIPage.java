package com.example.springaop.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

//@Route("status")
public class UsersStatusUIPage extends VerticalLayout{

	private UsersListUI usersList;
	
	@Autowired
	public UsersStatusUIPage(){
		this.usersList = new UsersListUI();
		
		add(getTitle(), usersList);
	}
	
	private Label getTitle() {
		Label title = new Label("Users Status");
		title.setWidth("100px");
		return title;
	}
	
}