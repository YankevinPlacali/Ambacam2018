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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "object_id_class", "object_id_identity" }))
public class AclObjectIdentity implements Serializable {
	private static final long serialVersionUID = -2386930544511643381L;

	@Id
	@GeneratedValue
	@Column(columnDefinition = "bigserial")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "object_id_class", columnDefinition = "bigint", nullable = false)
	private AclClass objectIdClass;

	@Column(name = "object_id_identity", columnDefinition = "bigint", nullable = false)
	private Long objectIdIdentity;

	@ManyToOne
	@JoinColumn(name = "parent_object", columnDefinition = "bigint")
	private AclObjectIdentity parentObject;

	@ManyToOne
	@JoinColumn(name = "owner_sid", columnDefinition = "bigint")
	private AclSid ownerSid;

	@Column(nullable = false)
	private boolean entriesInheriting;

	public AclObjectIdentity() {

	}

	public AclObjectIdentity(AclClass objectIdClass, Long objectIdIdentity, AclObjectIdentity parentObject,
			AclSid ownerSid, boolean entriesInheriting) {
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

	public long getObjectIdIdentity() {
		return objectIdIdentity;
	}

	public void setObjectIdIdentity(long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public AclObjectIdentity getParentObject() {
		return parentObject;
	}

	public void setParentObject(AclObjectIdentity parentObject) {
		this.parentObject = parentObject;
	}

	public AclSid getOwnerSid() {
		return ownerSid;
	}

	public void setOwnerSid(AclSid ownerSid) {
		this.ownerSid = ownerSid;
	}

	public boolean getEntriesInheriting() {
		return entriesInheriting;
	}

	public void setEntriesInheriting(boolean entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

}
