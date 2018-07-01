package com.ambacam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.ambacam.utils.DateUtils;

@Entity
public class Log implements Serializable {

	private static final long serialVersionUID = -8019398584857375323L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(columnDefinition = "varchar", nullable = true)
	private String description;

	private Date date;

	public Log() {

	}

	public Log(LogActeur acteurActif, LogActeur acteurPassif, Action action, MotifSuppression motif) {
		this.date = new DateTime().toDate();
		this.description = this.getDescriptionFromParameters(acteurActif, acteurPassif, action, motif, this.date);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@SuppressWarnings("deprecation")
	private String getDescriptionFromParameters(LogActeur acteurActif, LogActeur acteurPassif, Action action,
			MotifSuppression motif, Date date) {
		return String.format(
				"The object [%s (%s)] " + "performed the action [%s] on the object "
						+ "[%s (%s)] on the date [%d-%d-%d] at [%d:%d:%d] " + "for the following reason [%s]",
				acteurActif.getActeur().toString(), acteurActif.getPropertiesAsString(), action.getNom(),
				acteurPassif.getActeur().toString(), acteurPassif.getPropertiesAsString(), (date.getDay() + 1),
				(date.getMonth() + 1), (DateUtils.getActualYear(date)), date.getHours(), date.getMinutes(),
				date.getSeconds(), motif.getNom());
	}

}
