package com.youtube.jwt.controller;

import com.youtube.jwt.entity.OrderInput;
import com.youtube.jwt.service.OrderDetailService;
import com.youtube.jwt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('User')")
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderInput orderInput){
        orderDetailService.placeOrder(orderInput);
    }
}
