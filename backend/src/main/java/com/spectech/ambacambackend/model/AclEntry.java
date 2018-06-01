package com.spectech.ambacambackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AclEntry implements Serializable {
	private static final long serialVersionUID = -2380144437865737694L;

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private AclObjectIdentity objectIdClass;
	private Long acl_order;
	private AclSid sid;
	private boolean mask;
	private boolean granting;
	private boolean audit_success;
	private boolean audit_failure;

	public AclEntry() {

	}

	public AclEntry(Long id, AclObjectIdentity objectIdClass, Long acl_order, AclSid sid, boolean mask,
			boolean granting, boolean audit_success, boolean audit_failure) {
		this.id = id;
		this.objectIdClass = objectIdClass;
		this.acl_order = acl_order;
		this.sid = sid;
		this.mask = mask;
		this.granting = granting;
		this.audit_success = audit_success;
		this.audit_failure = audit_failure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AclObjectIdentity getObjectIdClass() {
		return objectIdClass;
	}

	public void setObjectIdClass(AclObjectIdentity objectIdClass) {
		this.objectIdClass = objectIdClass;
	}

	public Long getAcl_order() {
		return acl_order;
	}

	public void setAcl_order(Long acl_order) {
		this.acl_order = acl_order;
	}

	public AclSid getSid() {
		return sid;
	}

	public void setSid(AclSid sid) {
		this.sid = sid;
	}

	public boolean isMask() {
		return mask;
	}

	public void setMask(boolean mask) {
		this.mask = mask;
	}

	public boolean isGranting() {
		return granting;
	}

	public void setGranting(boolean granting) {
		this.granting = granting;
	}

	public boolean isAudit_success() {
		return audit_success;
	}

	public void setAudit_success(boolean audit_success) {
		this.audit_success = audit_success;
	}

	public boolean isAudit_failure() {
		return audit_failure;
	}

	public void setAudit_failure(boolean audit_failure) {
		this.audit_failure = audit_failure;
	}

}
