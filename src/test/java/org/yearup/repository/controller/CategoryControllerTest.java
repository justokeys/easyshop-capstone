package org.yearup.repository.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.yearup.controllers.CategoriesController;
import org.yearup.models.Category;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;

@WebMvcTest(CategoriesController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;
    @MockitoBean
    private ProductService productService;

    @Test
    void getALLCategories_ShouldReturnAllCategories() throws Exception{

        Category category1 = new Category(1,"Justo","App Dev");

        Category category2 = new Category(2,"messi","soccer Dev");

        List<Category> categories = Arrays.asList(category1,category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].categoryId").value(1))
                .andExpect(jsonPath("$[0].name").value("Justo"))
                .andExpect(jsonPath("$[0].description").value("App Dev"))
                .andExpect(jsonPath("$[1].categoryId").value(2))
                .andExpect(jsonPath("$[1].name").value("messi"))
                .andExpect(jsonPath("$[1].description").value("soccer Dev"));


    }

    @Test
    void get

}
