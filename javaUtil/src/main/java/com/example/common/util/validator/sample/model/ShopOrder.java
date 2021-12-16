package com.example.common.util.validator.sample.model;

import com.example.common.util.validator.EnumValueValidator;
import com.example.common.util.validator.sample.enums.PayTypeEnum;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder(toBuilder = true)
@ToString
public class ShopOrder {

    @EnumValueValidator(enumClass = PayTypeEnum.class , enumMethod = "isValueValid" , message = "支付类型校验有误")
    @NotNull(message = "支付类型必须传")
    private String payType;

}
