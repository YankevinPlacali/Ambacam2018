package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contact implements Serializable {
	private static final long serialVersionUID = 172083357911117092L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public Contact() {

	}

	public Contact(String name) {
		this.name = name;
	}

	public Contact(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
