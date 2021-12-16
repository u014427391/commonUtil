package com.example.common.util.validator.sample.controller;

import com.example.common.util.validator.sample.model.ShopOrder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/orders")
public class SampleController {

    @PostMapping
    public String saveOrder(@Validated ShopOrder shopOrder) {
        return shopOrder.toString();
    }
}
