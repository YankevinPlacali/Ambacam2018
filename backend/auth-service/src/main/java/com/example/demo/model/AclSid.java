package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "sid", "principal" }))
public class AclSid implements Serializable {
	private static final long serialVersionUID = -6348303417623965614L;

	@Id
	@GeneratedValue
	@Column(columnDefinition = "bigserial")
	private Long id;

	@Column(nullable = false)
	private boolean principal;

	@Column(columnDefinition = "varchar", nullable = false)
	private String sid;

	public AclSid() {

	}

	public AclSid(boolean principal, String sid) {
		this.principal = principal;
		this.sid = sid;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public AclSid principal(boolean principal) {
		this.principal = principal;
		return this;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public AclSid sid(String sid) {
		this.sid = sid;
		return this;
	}

}
