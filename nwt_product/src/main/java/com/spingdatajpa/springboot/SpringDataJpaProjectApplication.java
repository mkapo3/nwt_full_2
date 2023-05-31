package com.spingdatajpa.springboot;

import com.spingdatajpa.springboot.Repository.CategoryRepository;
import com.spingdatajpa.springboot.Repository.ProductRepository;
import com.spingdatajpa.springboot.Repository.StateRepository;
import com.spingdatajpa.springboot.entity.Category;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;
@SpringBootApplication
public class SpringDataJpaProjectApplication implements CommandLineRunner {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	ProductRepository productRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaProjectApplication.class, args);
	}
    @Override
	public void run(String...args){

		/*Category category = new Category("Kategorija");
		categoryRepository.save(category);
		State state = new State(39, 0.10, 38);
		stateRepository.save(state);
		Product product = new Product("Nike patike", "bijela", "Koza", "Zenske", category, List.of(state), 0);

		productRepository.save(product);*/
	}
}
