package com.spectech.ambacambackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AclSid implements Serializable {
	private static final long serialVersionUID = -3143183169677262255L;

	@Id
	@GeneratedValue
	private Long id;
	private boolean principal;
	private String sid;

	public AclSid() {

	}

	public AclSid(Long id, boolean principal, String sid) {
		this.id = id;
		this.principal = principal;
		this.sid = sid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
