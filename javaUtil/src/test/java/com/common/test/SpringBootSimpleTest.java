package com.common.test;

import com.example.common.util.CommonApplication;
import com.example.common.util.validator.sample.model.ShopOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CommonApplication.class)
public class SpringBootSimpleTest {

    @Test
    public void test() {
        ShopOrder shopOrder = ShopOrder.builder().payType("11").build();
        System.out.println(shopOrder.toString());
    }

}
