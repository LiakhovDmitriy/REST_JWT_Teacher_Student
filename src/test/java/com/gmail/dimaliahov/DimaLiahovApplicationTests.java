package com.gmail.dimaliahov;

import com.gmail.dimaliahov.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DimaLiahovApplicationTests
{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LoginController loginController;

	@Test
	void test () throws Exception {
		this.mockMvc.perform(get("/api/login"))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
