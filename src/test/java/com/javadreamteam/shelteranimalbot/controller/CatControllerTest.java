package com.javadreamteam.shelteranimalbot.controller;

import com.javadreamteam.shelteranimalbot.controllers.*;
import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.repository.*;
import com.javadreamteam.shelteranimalbot.service.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (CatController.class)
public class CatControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CatRepository catRepository;



	@MockBean
	private CatService catService;



	@InjectMocks
	private CatController catController;


	private final Cat animal = new Cat();
	private final JSONObject jsonAnimal = new JSONObject();

	@BeforeEach
	public void setup() throws Exception {

		animal.setId(1L);
		animal.setName("Мурка");
		animal.setAge(2);
		animal.setBreed("Британская");
		animal.setInfo("В крапинку");

		jsonAnimal.put("id", animal.getId());
		jsonAnimal.put("name", animal.getName());
		jsonAnimal.put("age", animal.getAge());
		jsonAnimal.put("breed", animal.getBreed());
		jsonAnimal.put("info", animal.getInfo());

		when(catService.create(animal)).thenReturn(animal);
		when(catService.update(animal)).thenReturn(animal);
		when(catService.getById(any())).thenReturn(animal);
		when(catService.getAll()).thenReturn(List.of(animal));
	}

	@Test
	public void createCat () throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.post("/cats")
						.content(jsonAnimal.toString())
						.contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(animal.getName()))
				.andExpect(jsonPath("$.age").value(animal.getAge()))
				.andExpect(jsonPath("$.breed").value(animal.getBreed()))
				.andExpect(jsonPath("$.info").value(animal.getInfo()));
	}

	@Test
	public void updateCat () throws Exception {
		animal.setName("New name");
		mockMvc.perform(MockMvcRequestBuilders
						.put("/cats")
						.content(jsonAnimal.toString())
						.contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(animal.getName()))
				.andExpect(jsonPath("$.age").value(animal.getAge()))
				.andExpect(jsonPath("$.breed").value(animal.getBreed()))
				.andExpect(jsonPath("$.info").value(animal.getInfo()));
	}

	@Test
	public void removeCat () throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/cats/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getCat () throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/cats/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void getCatAll () throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/cats/find-all-cats")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
