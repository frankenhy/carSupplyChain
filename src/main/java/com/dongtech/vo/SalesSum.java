package com.dongtech.vo;

import java.io.Serializable;

/**
 *   销量统计
 *   @author  PF
 */
public class SalesSum implements Serializable {
    private String name;
    private double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
