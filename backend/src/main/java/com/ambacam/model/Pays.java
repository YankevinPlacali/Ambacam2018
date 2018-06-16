package com.ambacam.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "The name must not be empty")
    @NotNull(message = "The name must not be null")
    @Column(columnDefinition = "VARCHAR", nullable = false)
    private String name;

    public Country() {
    }

    public Country(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country id(Long id){
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country name(String name){
        this.name = name;
        return this;
    }
}
