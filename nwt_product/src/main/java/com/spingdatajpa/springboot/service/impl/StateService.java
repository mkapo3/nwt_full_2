package com.spingdatajpa.springboot.service.impl;

import com.spingdatajpa.springboot.Repository.StateRepository;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import com.spingdatajpa.springboot.service.IStateService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class StateService implements IStateService {
    StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State createState(State state) {
        stateRepository.save(state);
        return state;
    }

    @Override
    public State updateState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public void deleteState(int id) {
        stateRepository.deleteById(id);
    }

    @Override
    public State getState(int id) {
        return stateRepository.findAllById(id);

    }

    @Override
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public State updateStateDiscount(int id, double p) {
        State s = stateRepository.findAllById(id);
        s.setProcenat(p);
        return stateRepository.save(s);
    }


}
