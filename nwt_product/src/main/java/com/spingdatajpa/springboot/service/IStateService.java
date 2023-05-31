package com.spingdatajpa.springboot.service;

import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;

import java.util.List;
import java.util.Optional;

public interface IStateService {

    public State createState(State state);
    public State updateState(State state);
    public void deleteState(int id);
    public State getState(int id);
    public List<State> getAllStates();
    public State updateStateDiscount(int id, double p);

}
