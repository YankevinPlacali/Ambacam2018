package com.ambacam.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RequeteGroupe extends AuditingCommonEntity implements Serializable {
	private static final long serialVersionUID = -909404128014016669L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "The name must not be empty")
	@NotNull(message = "The name must not be null")
	@Column(columnDefinition = "varchar", nullable = false)
	private String nom;

	@Column(columnDefinition = "varchar")
	private String description;

	@ManyToOne
	@JoinColumn(name = "cree_par_id", nullable = false, updatable = false)
	private Operateur creePar;

	@OneToMany(mappedBy = "requeteGroupe", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Requete> requetes = new HashSet<>();

	public RequeteGroupe() {
	}

	public RequeteGroupe(String nom) {
		this.nom = nom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequeteGroupe id(Long id) {
		this.id = id;
		return this;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public RequeteGroupe nom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequeteGroupe description(String description) {
		this.description = description;
		return this;
	}

	public Operateur getCreePar() {
		return creePar;
	}

	public void setCreePar(Operateur creePar) {
		this.creePar = creePar;
	}

	public String getStatus() {

		Set<String> statuses = requetes.stream().map(requete -> requete.getStatus().getNom())
				.collect(Collectors.toSet());

		if (statuses.isEmpty()) {
			return StatusRequeteValues.DRAFT;
		} else {
			if (statuses.size() == 1) {
				return statuses.iterator().next();
			} else {
				return StatusRequeteValues.MIXED;
			}
		}
	}
}
