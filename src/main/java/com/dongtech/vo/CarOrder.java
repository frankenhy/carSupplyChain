package com.dongtech.vo;

import java.math.BigDecimal;

/**
 * @author gzl
 * @date 2020-04-15
 * @program: springboot-jsp
 * @description: ${description}
 */
public class CarOrder {
    private Long id;
    private String number;
    private BigDecimal price;
    private int tearDownFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTearDownFlag() {
        return tearDownFlag;
    }

    public void setTearDownFlag(int tearDownFlag) {
        this.tearDownFlag = tearDownFlag;
    }

    public CarOrder(Long id, String number, BigDecimal price, int tearDownFlag) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.tearDownFlag = tearDownFlag;
    }
}
