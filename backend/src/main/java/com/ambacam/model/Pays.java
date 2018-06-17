package com.ambacam.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
public class Pays implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "The nom must not be empty")
    @NotNull(message = "The nom must not be null")
    @Column(columnDefinition = "VARCHAR", nullable = false)
    private String nom;

    public Pays() {
    }

    public Pays(String nom) {
        super();
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pays id(Long id){
        this.id = id;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Pays nom(String nom){
        this.nom = nom;
        return this;
    }
}
