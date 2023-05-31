package com.spingdatajpa.springboot.controller;

import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import com.spingdatajpa.springboot.error.exception.WrappedException;
import com.spingdatajpa.springboot.service.impl.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.spingdatajpa.springboot.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/product/state")
public class StateController {

    StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }
    @GetMapping("/{id}")
    public State getState(@PathVariable("id") int id){
        State s = stateService.getState(id);
        if(s==null){
            throw new WrappedException(NOT_FOUND);
        }
        return s;
    }
    @DeleteMapping("/{id}")
    public void deleteState(@PathVariable("id") int id){
        State s = stateService.getState(id);
        if(s==null){
            throw new WrappedException(NOT_FOUND);
        }
        stateService.deleteState(id);
    }
    @GetMapping("/states")
    public ResponseEntity<List<State>> getAllStates(){
        return new ResponseEntity<>(stateService.getAllStates(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<State> updateState(@PathVariable int id, @RequestBody State state){
        State s = stateService.getState(id);
        if(s==null){
            throw new WrappedException(NOT_FOUND);
        }
        return new ResponseEntity<>(stateService.updateState(state), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<State> createState(@RequestBody State state){
        return new ResponseEntity<>(stateService.createState(state), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}/createDiscount")
    public ResponseEntity<State> createDiscount(@PathVariable int id, double p){
        State s = stateService.getState(id);
        if(s==null){
            throw new WrappedException(NOT_FOUND);
        }
        return new ResponseEntity<>(stateService.updateStateDiscount(id, p), HttpStatus.OK);
    }




}
