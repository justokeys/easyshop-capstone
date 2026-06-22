package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(int categoryId) {
        // get category by id
        return categoryRepository.findById(categoryId);
    }

    public Category create(Category category)
    {

        // create a new category
        return categoryRepository.save(category);
    }

    public Category updateCategory(int categoryId, Category category)
    {
        Category existing = categoryRepository.findById(categoryId).orElseThrow();
        existing.setDescription(category.getDescription());
        existing.setName(category.getName());
        // update category and return the updated category
        return categoryRepository.save(existing) ;
    }

    public void delete(int categoryId)
    {
        // delete category
        Category deleted = categoryRepository.findById(categoryId).orElseThrow();
        categoryRepository.delete(deleted);
    }
}
