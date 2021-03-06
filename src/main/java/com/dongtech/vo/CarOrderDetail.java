package com.dongtech.vo;

import java.math.BigDecimal;

/**
 * @author gzl
 * @date 2020-04-15
 * @program: springboot-jsp
 * @description: ${description}
 */
public class CarOrderDetail {
    private Long id;
    private String goodsname;
    private Integer num;
    //出厂地址
    private String produce;
    //单价
    private BigDecimal price;

    private Long orderId;

    public CarOrderDetail(Long id, String goodsname, Integer num, String produce, BigDecimal price, Long orderId) {
        this.id = id;
        this.goodsname = goodsname;
        this.num = num;
        this.produce = produce;
        this.price = price;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
