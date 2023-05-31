package com.spingdatajpa.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

@Entity
public class State {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotBlank(message = "Cijena is mandatory")
    @Min(1)
    private double cijena;
    @Min(0)
    @Max(1)
    private double procenat;
    @Min(28)
    @Max(47)
    private int velicina;

    @ManyToOne
    private Product proizvod;

   // @OneToMany(mappedBy = "state")
    //private List<Product> proizvod;


    public State() {
    }

    public State(double cijena, double procenat, int velicina) {

        this.cijena = cijena;
        this.procenat = procenat;
        this.velicina = velicina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public double getProcenat() {
        return procenat;
    }

    public void setProcenat(double procenat) {
        this.procenat = procenat;
    }

    public int getVelicina() {
        return velicina;
    }

    public void setVelicina(int velicina) {
        this.velicina = velicina;
    }


}
