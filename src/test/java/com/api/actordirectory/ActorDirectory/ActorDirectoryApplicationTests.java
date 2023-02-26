package com.api.actordirectory.ActorDirectory;

import com.api.actordirectory.ActorDirectory.model.Actor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ActorDirectoryApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@Test
	void contextLoads() {
	}

	@Test
	public void findByIdApiTest() throws Exception {
		String actorId="1002";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/findById").param("actorId",actorId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.actorId").isNotEmpty());

	}

	@Test
	public void findByIdApiNotFoundTest() throws Exception {
		String actorId="1190";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/findById").
				param("actorId",actorId)).equals("Actor not exists in ActorDirectory");
				//.andExpect(MockMvcResultMatchers.content("Actor not exists in ActorDirectory").equals()));
	}

	@Test
	public void findByNameApiTest() throws Exception {
		String actorname="Jeff";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/findByName").param("name",actorname)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$..name").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$..actorId").isNotEmpty());

	}

	@Test
	public void findByNameApiNotFoundTest() throws Exception {
		String name="asdfg";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/findByName").
				param("name",name)).equals("Actor not exists in ActorDirectory");
		//.andExpect(MockMvcResultMatchers.content("Actor not exists in ActorDirectory").equals()));
	}

	@Test
	public void saveActorApiTest() throws Exception {
		Actor actor=new Actor("Swetha",5.0,"19 October 1987");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/saveActor").content(asJsonString(actor))).andDo(print())
				.equals("Actor saved successfully");

	}
	@Test
	public void updateActorApiTest() throws Exception {
		Actor actor=new Actor("Jeff Bridges",1002,5.0,"19 October 1987");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/updateActor").
				content(asJsonString(actor))).equals("Actor details updated successfully");
	}
	@Test
	public void updateActorApiNotFoundTest() throws Exception {
		Actor actor=new Actor("Jeff Bridges",1199,5.0,"19 October 1987");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/actors/updateActor").
				content(asJsonString(actor))).equals("Actor not found in the directory");
	}
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
