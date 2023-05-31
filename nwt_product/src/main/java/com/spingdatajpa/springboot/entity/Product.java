package com.spingdatajpa.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Product {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Color is mandatory")
    private String color;
    @NotBlank(message = "Sastav is mandatory")
    private String sastav;
    @OneToMany
    private List<State> state;
    @ManyToOne
    private Category category;
    @NotBlank(message = "Spol is mandatory")
    private String spol;

    public Product(Integer id, String name, String color, String sastav, String spol) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sastav = sastav;
        this.state = state;
        this.category = category;
        this.spol = spol;
    }

    public Product(String name, String color, String sastav, String spol, Category categoryId, List<State> stanje1, int rezrevacijaId) {

        this.name = name;
        this.color = color;
        this.sastav = sastav;
        this.spol = spol;
        category = categoryId;
        state = stanje1;
        RezrevacijaId = rezrevacijaId;
    }

    public Product() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }



    private int RezrevacijaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String naziv) {
        this.name = naziv;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String boja) {
        this.color = boja;
    }

    public String getSastav() {
        return sastav;
    }

    public void setSastav(String sastav) {
        this.sastav = sastav;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public int getRezrevacijaId() {
        return RezrevacijaId;
    }

    public void setRezrevacijaId(int rezrevacijaId) {
        RezrevacijaId = rezrevacijaId;
    }



}
