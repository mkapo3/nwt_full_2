package com.spingdatajpa.springboot.Repository;

import com.spingdatajpa.springboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Category getById(Integer id);

    Category findAllById(Integer id);
}
