package com.retail.dao;

public class PriceInfo {

    private String currency;
    private String price;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public PriceInfo() {
        super();
    }

    public PriceInfo(String currency, String price) {
        super();
        this.currency = currency;
        this.price = price;
    }
}
