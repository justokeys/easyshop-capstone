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
    private ShoppingCartService shoppingCartService;
    private ProfileService profileService;

    public OrderService(OrderLineItemRepository orderLineItemRepository, OrderRepository orderRepository, ShoppingCartService shoppingCartService, ProfileService profileService) {
        this.orderLineItemRepository = orderLineItemRepository;
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileService = profileService;
    }

    public Order getByUserId(int userId) {
        Profile userProfile = profileService.getProfile(userId).orElseThrow();
        ShoppingCart theCart = shoppingCartService.getByUserId(userId);
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


        for(ShoppingCartItem item : theCart.getItems().values()){
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setProductId(item.getProductId());
            lineItem.setSalesPrice(item.getProduct().getPrice());
            lineItem.setQuantity(item.getQuantity());
            lineItem.setDiscount(item.getDiscountPercent());
            lineItem.setOrderId(newOrderId);

            orderLineItemRepository.save(lineItem);
        }

        shoppingCartService.deleteById(userId);

        return newOrder;
    }
}
