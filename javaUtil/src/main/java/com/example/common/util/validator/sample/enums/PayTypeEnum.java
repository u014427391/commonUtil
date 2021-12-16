package com.example.common.util.validator.sample.enums;



public enum PayTypeEnum {

    Cash("1","现金"),
    Alipay("2","支付宝"),
    WeChatPay("3","微信支付"),
    BankCard("4","银行卡支付"),
    CreditCard("5","信用卡支付");

    PayTypeEnum(String code , String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean isValueValid(String value) {
        for (PayTypeEnum typeEnum : values()) {
            if (!typeEnum.getCode().equals(value))
                return false;
        }
        return true;
    }

}
