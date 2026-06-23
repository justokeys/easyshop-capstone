package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

// convert this class to a REST controller
@RestController
@RequestMapping("/shoppingcart")
@CrossOrigin(origins = "*")
// only logged in users should have access to these actions
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    // a shopping cart controller depends on the service layer
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;


    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShoppingCart> getCart(Principal principal)
    {
        // get the currently logged in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // use the shoppingCartService to get all items in the cart and return the cart
        return ResponseEntity.ok().body(shoppingCartService.getByUserId(userId)) ;
    }


    @GetMapping("userId/productid/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartItem> getCart(Principal principal, int productId)
    {
        // get the currently logged in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // use the shoppingCartService to get all items in the cart and return the cart
        return ResponseEntity.ok().body(shoppingCartService.getByByUserIdAndProductId(userId,productId));
    }


    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created

    @PostMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShoppingCart>  addProductById(Principal principal,@PathVariable int productId){
        // get the currently logged in username
                String userName = principal.getName();
                // find database user by username
                User user = userService.getByUserName(userName);
                int userId = user.getId();

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(shoppingCartService.addProductByUserIdAndProductId(userId,productId));
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)
    @PutMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShoppingCart> updateProductByProductId(Principal principal , @RequestBody ShoppingCartItem item, @PathVariable int productId){
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        int quantityUpdate = item.getQuantity();

        return ResponseEntity.ok().body(shoppingCartService.updateProductByIdAndUserId(userId,productId, quantityUpdate));

    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)

}
