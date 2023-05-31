package com.spingdatajpa.springboot.Repository;

import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {

    State findAllById(int id);
}
