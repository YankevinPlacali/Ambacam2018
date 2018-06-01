package com.spectech.ambacambackend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AclClass implements Serializable {
	private static final long serialVersionUID = -8503669554614059633L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "class")
	private String _class;

	public AclClass() {

	}

	public AclClass(Long id, String _class) {
		this.id = id;
		this._class = _class;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

}
