package com.example.common.util.validator.sample.controller;

import com.example.common.util.validator.sample.model.ShopOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class SampleController {

    @RequestMapping("/test")
    public String test() {
        ShopOrder shopOrder = ShopOrder.builder().payType("11").build();
        return shopOrder.toString();
    }
}
