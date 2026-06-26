package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.Order;
import org.yearup.models.User;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/orders")

@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Order> getByUserId(Principal principal) {
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();


        return ResponseEntity.ok().body(orderService.getByUserId(userId));
    }
}
