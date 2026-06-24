package org.yearup.controllers;

import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;
import java.util.Optional;

// add the annotations to make this a REST controller
@RestController
// add the annotation to make this controller the endpoint for the following url
@RequestMapping("/categories")
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
@CrossOrigin(origins = "*")
public class CategoriesController
{
    private final CategoryService categoryService;
    private final ProductService productService;




    // create an Autowired constructor to inject the categoryService and productService
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }
    @GetMapping
    // add the appropriate annotation for a get action
    public ResponseEntity<List<Category>> getAllCategories()
    {
        List<Category> categoryList = categoryService.getAllCategories();
        // find and return all categories
        return ResponseEntity.ok(categoryList);
    }

    // add the appropriate annotation for a get action
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getById(@PathVariable int categoryId)
    {
        // get the category by id
        return categoryService.getById(categoryId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId)
    {
        List<Product> productList = productService.listByCategoryId(categoryId);
        // get a list of product by categoryId
        return ResponseEntity.ok().body(productList);
    }

    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        // insert the category and return it with status 201 Created
        Category created = categoryService.create(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PutMapping("/{categoryId}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable int categoryId, @RequestBody Category category) {

        // update the category by id and return the updated category (200 OK)

        if (categoryService.getById(categoryId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Category updated = categoryService.updateCategory(categoryId,category);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }



    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId)
    {
        if (categoryService.getById(categoryId) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // delete the category by id and return status 204 No Content;
        categoryService.delete(categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
