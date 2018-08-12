package com.ambacam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Requete implements Serializable {
	private static final long serialVersionUID = 3382958287248625084L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "status_requete_id", nullable = false)
	private StatusRequete status;

	@ManyToOne
	@JoinColumn(name = "type_requete_id", nullable = false)
	private TypeRequete type;

	private Date date;

	@ManyToOne
	@JoinColumn(name = "requete_id", nullable = false, updatable = false)
	private Requerant requerant;

	@ManyToOne
	@JoinColumn(name = "operateur_id", nullable = false)
	private Operateur operateur;

	@ManyToOne
	@JoinColumn(name = "requete_groupe_id")
	private RequeteGroupe requeteGroupe;

	public Requete() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Requete id(Long id) {
		this.id = id;
		return this;
	}

	public StatusRequete getStatus() {
		return status;
	}

	public void setStatus(StatusRequete status) {
		this.status = status;
	}
	
	public Requete status(StatusRequete status) {
            this.status = status;
            return this;
    }

	public TypeRequete getType() {
		return type;
	}

	public void setType(TypeRequete type) {
		this.type = type;
	}
	
	public Requete type(TypeRequete type) {
            this.type = type;
            return this;
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Requete date(Date date) {
            this.date = date;
            return this;
    }

	public Requerant getRequerant() {
		return requerant;
	}

	public void setRequerant(Requerant requerant) {
		this.requerant = requerant;
	}

	public Requete requerant(Requerant requerant) {
            this.requerant = requerant;
            return this;
    }
	
	public Operateur getOperateur() {
		return operateur;
	}

	public void setOperateur(Operateur operateur) {
		this.operateur = operateur;
	}
	
	public Requete operateur(Operateur operateur) {
            this.operateur = operateur;
            return this;
    }

	public RequeteGroupe getRequeteGroupe() {
		return requeteGroupe;
	}

	public void setRequeteGroupe(RequeteGroupe requeteGroupe) {
		this.requeteGroupe = requeteGroupe;
	}
	
	public Requete requeteGroupe(RequeteGroupe requeteGroupe) {
            this.requeteGroupe = requeteGroupe;
            return this;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Requete other = (Requete) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

}
