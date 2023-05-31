package com.spingdatajpa.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
public class Category {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @OneToMany(mappedBy = "category")
    private Set<Product> proizvodSet;
    @NotBlank(message = "Name is mandatory")
    private String naziv;

    public Category(String naziv) {
        this.naziv = naziv;
    }

    public Category(Integer id, Set<Product> productSet, String naziv) {
        this.id = id;
        this.proizvodSet = productSet;
        this.naziv = naziv;
    }

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }




}
