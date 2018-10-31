package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "acl_object_identity", "ace_order" }))
public class AclEntry implements Serializable {
	private static final long serialVersionUID = 9054397219354234229L;

	@Id
	@GeneratedValue
	@Column(columnDefinition = "bigserial")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "acl_object_identity", columnDefinition = "bigint", nullable = false)
	private AclObjectIdentity aclObjectIdentity;

	@Column(name = "ace_order", nullable = false)
	private int aceOrder;

	@ManyToOne
	@JoinColumn(name = "sid", columnDefinition = "bigint", nullable = false)
	private AclSid sid;

	@Column(nullable = false)
	private int mask;

	@Column(nullable = false)
	private boolean granting;

	@Column(nullable = false)
	private boolean auditSuccess;

	@Column(nullable = false)
	private boolean auditFailure;

	public AclEntry() {

	}

	public AclEntry(AclObjectIdentity aclObjectIdentity, int aceOrder, AclSid sid, int mask, boolean granting,
			boolean auditSuccess, boolean auditFailure) {
		this.aclObjectIdentity = aclObjectIdentity;
		this.aceOrder = aceOrder;
		this.sid = sid;
		this.mask = mask;
		this.granting = granting;
		this.auditSuccess = auditSuccess;
		this.auditFailure = auditFailure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AclObjectIdentity getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	public int getAceOrder() {
		return aceOrder;
	}

	public void setAceOrder(int aceOrder) {
		this.aceOrder = aceOrder;
	}

	public AclSid getSid() {
		return sid;
	}

	public void setSid(AclSid sid) {
		this.sid = sid;
	}

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	public boolean getGranting() {
		return granting;
	}

	public void setGranting(boolean granting) {
		this.granting = granting;
	}

	public boolean getAuditSuccess() {
		return auditSuccess;
	}

	public void setAuditSuccess(boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public boolean getAuditFailure() {
		return auditFailure;
	}

	public void setAuditFailure(boolean auditFailure) {
		this.auditFailure = auditFailure;
	}

}
