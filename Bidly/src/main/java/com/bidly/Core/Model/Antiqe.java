package com.bidly.Core.Model;

public class Antiqe {

    private int antiqe_id;
    private String name;
    private String description;
    private String pic_url;
    private int price;
    private int last_bid_price;
    private int store_id;
    private String storeName;

    public Antiqe( int antiqe_id, String name, String description, String pic_url, int price, int store_id ) {
        this.antiqe_id = antiqe_id;
        this.name = name;
        this.description = description;
        this.pic_url = pic_url;
        this.price = price;
        this.store_id = store_id;
    }

    public Antiqe( int antiqe_id, String name, String description, String pic_url, int price, int last_bid_price, int store_id, String storeName ) {
        this.antiqe_id = antiqe_id;
        this.name = name;
        this.description = description;
        this.pic_url = pic_url;
        this.price = price;
        this.last_bid_price = last_bid_price;
        this.store_id = store_id;
        this.storeName = storeName;
    }

    // GETTERS
    public int getAntiqe_id() {return antiqe_id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getPic_url() {return pic_url;}
    public int getPrice() {return price;}
    public int getLast_bid_price(){return last_bid_price;}
    public int getStore_id() {return store_id;}
    public String getStoreName(){return storeName;}

    // SETTERS
    public void setAntiqe_id(int antiqe_id) {this.antiqe_id = antiqe_id;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setPic_url(String pic_url) {this.pic_url = pic_url;}
    public void setPrice(int price) {this.price = price;}
    public void setLast_bid_price(int last_bid_price){this.last_bid_price=last_bid_price;}
    public void setStore_id(int store_id) {this.store_id = store_id;}
    public void setStoreName(String storeName){this.storeName=storeName;}

}
