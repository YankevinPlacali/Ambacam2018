package com.spectech.ambacambackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AclObjectIdentity implements Serializable {
	private static final long serialVersionUID = -385778565676241020L;

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private AclClass objectIdClass;
	private Long objectIdIdentity;
	private Long parentObject;
	@ManyToOne
	private AclSid ownerSid;
	private boolean entriesInheriting;

	public AclObjectIdentity() {
	}

	public AclObjectIdentity(Long id, AclClass objectIdClass, Long objectIdIdentity, Long parentObject, AclSid ownerSid,
			boolean entriesInheriting) {
		this.id = id;
		this.objectIdClass = objectIdClass;
		this.objectIdIdentity = objectIdIdentity;
		this.parentObject = parentObject;
		this.ownerSid = ownerSid;
		this.entriesInheriting = entriesInheriting;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AclClass getObjectIdClass() {
		return objectIdClass;
	}

	public void setObjectIdClass(AclClass objectIdClass) {
		this.objectIdClass = objectIdClass;
	}

	public Long getObjectIdIdentity() {
		return objectIdIdentity;
	}

	public void setObjectIdIdentity(Long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public Long getParentObject() {
		return parentObject;
	}

	public void setParentObject(Long parentObject) {
		this.parentObject = parentObject;
	}

	public AclSid getOwnerSid() {
		return ownerSid;
	}

	public void setOwnerSid(AclSid ownerSid) {
		this.ownerSid = ownerSid;
	}

	public boolean isEntriesInheriting() {
		return entriesInheriting;
	}

	public void setEntriesInheriting(boolean entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

}
