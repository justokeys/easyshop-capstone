package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.*;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ShoppingCartRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class OrderService {
    private OrderLineItemRepository orderLineItemRepository;
    private OrderRepository orderRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartService shoppingCartService;
    private ProductService productService;
    private ProfileService profileService;

    public OrderService(OrderLineItemRepository orderLineItemRepository, OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, ShoppingCartService shoppingCartService, ProductService productService, ProfileService profileService) {
        this.orderLineItemRepository = orderLineItemRepository;
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.profileService = profileService;
    }

    public Order getByUserId(int userId) {
        Profile userProfile = profileService.getProfile(userId).orElseThrow();
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setZip(userProfile.getZip());
        newOrder.setState(userProfile.getState());
        newOrder.setAddress(userProfile.getAddress());
        newOrder.setCity(userProfile.getCity());
        newOrder.setDate(LocalDate.now());
        newOrder.setShippingAmount(2.99);
        newOrder = orderRepository.save(newOrder);

        int newOrderId = newOrder.getOrderId();

        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);


        for (CartItem itemList : cartItems) {
            Product products = productService.getById(itemList.getProductId());
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setProductId(itemList.getProductId());
            lineItem.setSalesPrice(products.getPrice());
            lineItem.setQuantity(itemList.getQuantity());
            lineItem.setOrderId(newOrderId);

            orderLineItemRepository.save(lineItem);


        }

        return newOrder;
    }
}
