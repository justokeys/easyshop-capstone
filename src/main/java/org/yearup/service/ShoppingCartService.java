package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId) {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        ShoppingCart cart = new ShoppingCart();

        // find the users items

        List<CartItem> userCartItems = shoppingCartRepository.findByUserId(userId);

        // find each product and add them to row in cart
        // find the quantity of each item and add them to row in cart
        for (CartItem cartItem : userCartItems) {
            Product products = productService.getById(cartItem.getProductId());
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(products);
            item.setQuantity(cartItem.getQuantity());
            cart.add(item);
        }

        return cart;
    }

    // add additional methods here

    public ShoppingCart addProductByUserIdAndProductId(int userId, int productId) {
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);


        if (cartItem == null) {

            CartItem cartItem2 = new CartItem();
            cartItem2.setProductId(productId);
            cartItem2.setUserId(userId);
            shoppingCartRepository.save(cartItem2);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            shoppingCartRepository.save(cartItem);
        }

        return getByUserId(userId);

    }

    public ShoppingCart updateProductByIdAndUserId(int userId, int productId, int quantity) {
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        cartItem.setQuantity(quantity);
        shoppingCartRepository.save(cartItem);

        return getByUserId(userId);

    }

    @Transactional
    public void deleteById(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }


    public CartItem getByByUserIdAndProductId(int userId, int productId) {

        return shoppingCartRepository.findByUserIdAndProductId(userId, productId);
    }

}
