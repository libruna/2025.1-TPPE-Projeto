package com.smartmanage.api.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmanage.api.SmartManageApplication;
import com.smartmanage.api.dto.request.CategoryRequestDto;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(classes = SmartManageApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> categoryProvider() {
        return Stream.of(
                Arguments.of("Brinquedos", "Jogos e diversão"),
                Arguments.of("Livros", "Leitura geral"),
                Arguments.of("Eletrônicos", "Dispositivos")
        );
    }

    @ParameterizedTest
    @MethodSource("categoryProvider")
    void shouldCreateAndRetrieveCategory(String name, String description) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName(name);
        dto.setDescription(description);

        // POST /categories
        String postJson = objectMapper.writeValueAsString(dto);
        MvcResult mvcRes = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        long id = objectMapper.readTree(mvcRes.getResponse().getContentAsString())
                .get("id").asLong();

        // GET /categories/{id}
        mockMvc.perform(get("/categories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description));

        // GET /categories (lista)
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[?(@.id==" + id + ")]").exists());
    }
}
