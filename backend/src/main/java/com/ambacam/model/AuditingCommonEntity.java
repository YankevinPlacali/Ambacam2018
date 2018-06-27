package com.ambacam.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingCommonEntity {

	@CreatedDate
	@Column(updatable = false)
	protected Date creeLe;

	public Date getCreeLe() {
		return creeLe;
	}

	public void setCreeLe(Date creeLe) {
		this.creeLe = creeLe;
	}

}
