package com.springboot.restapi.bean;

public class Product {
    private int prodId;
    private String prodName;
    private int prodConst;

    public Product(int prodId, String prodName, int prodConst) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodConst = prodConst;
    }

    public Product() {

    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getProdConst() {
        return prodConst;
    }

    public void setProdConst(int prodConst) {
        this.prodConst = prodConst;
    }
}
