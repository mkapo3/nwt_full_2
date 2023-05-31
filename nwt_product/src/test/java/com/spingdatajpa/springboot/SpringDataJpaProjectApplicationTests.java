package com.spingdatajpa.springboot;

import com.spingdatajpa.springboot.Repository.CategoryRepository;
import com.spingdatajpa.springboot.Repository.ProductRepository;
import com.spingdatajpa.springboot.Repository.StateRepository;
import com.spingdatajpa.springboot.entity.Category;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.webjars.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringDataJpaProjectApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	MockMvc mockMvc;

	private Product p1, p2, p3;
	private State s1, s2, s3;
	private Category c1, c2, c3;

	@BeforeAll
	void Setup(){
		s1 = new State(66, 0, 38);
		s2 = new State(109, 0, 43);
		s3 = new State(53, 0.2, 39);

		c1 = new Category("Patike");
		c2 = new Category("Sandale");
		c3 = new Category("Cipele");

		s1 = stateRepository.save(s1);
		s2 = stateRepository.save(s2);
		s3 =  stateRepository.save(s3);

		c1 = categoryRepository.save(c1);
		c2 = categoryRepository.save(c2);
		c3 = categoryRepository.save(c3);

		p1 = new Product("Nike", "Bijela", "Koza", "M", c1, List.of(s1), 0);
		p2 = new Product("Bonzu", "Smedja", "Koza", "Z", c2, List.of(s3), 0);
		p3 = new Product("Caprisa", "Crna", "Platno", "Z", c3, List.of(s2), 0);

		p1 = productRepository.save(p1);
		p2 = productRepository.save(p2);
		p3 = productRepository.save(p3);

	}
	@Test
	void getAllProductsByCategory() throws Exception{
		mockMvc.perform(get(String.format("/api/products/category/%s", c1.getNaziv())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].name").value("Nike"))
				.andExpect(jsonPath("$.[0].color").value("Bijela"));
	}
	@Test
	void addProductSuccessfully() throws Exception{
		mockMvc.perform(post(String.format("/api/product")).content(

				"{\n"+
						"      \"id\":0,\n" +
						"      \"name\":\"Nike\",\n" +
						"      \"color\":\"Bijela\",\n"+
						"      \"sastav\":\"Nike\",\n" +
						"      \"state\":null,\n"+
						"      \"category\":null,\n" +
						"      \"spol\":\"M\",\n"+
						"      \"rezervacija_id\":0,\n" +
						"}"
		).contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isCreated()).
				andExpect(jsonPath("$.name").value("Nike"));
	}
	@Test
	void addStateSuccessfully() throws Exception{
		mockMvc.perform(post(String.format("/sanje/create")).content(
				"{\n"+
						"  \"id\":0,\n" +
						"  \"cijena\":0,\n" +
						"  \"procenat\":0,\n" +
						"  \"velicina\":47,\n" +
						"}"
				).contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isCreated()).
				andExpect(jsonPath("$.cijena").value(0));
	}

	@Test
	void addProductWithExistingName() throws Exception {
		mockMvc.perform(put(String.format("/api/product/update/{%d}", 73)).content(

				"{\n"+
						"      \"id\":73,\n" +
						"      \"name\":\"Nike\",\n" +
						"      \"color\":\"Bijela\",\n"+
						"      \"sastav\":\"Nike\",\n" +
						"      \"state\":null,\n"+
						"      \"category\":null,\n" +
						"      \"spol\":\"M\",\n"+
						"      \"rezervacija_id\":0,\n" +
						"}"
		).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Product with id = 73 does not exist!"));
	}
	@Test
	void deleteProductInvalidId() throws Exception {
		Integer id =p2.getId()+1034;
		mockMvc.perform(delete(String.format("/api/%d", id))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Not found"))
				.andExpect(jsonPath("$.message").value("Role with id = " + id + " does not exist!"))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
	}
	@Test
	void deleteProduct() throws Exception {
		Integer id = p2.getId();
		mockMvc.perform(delete(String.format("/api/%d", id))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void contextLoads() {
	}

}
