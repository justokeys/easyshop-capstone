package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.service.OrderService;

@RestController
@RequestMapping("/orders")

@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private
}
