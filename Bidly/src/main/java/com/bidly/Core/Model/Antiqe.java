package com.bidly.Core.Model;

public class Antiqe {

    private int antiqe_id;
    private String name;
    private String description;
    private String pic_url;
    private int price;
    private int store_id;

    public Antiqe( int antiqe_id, String name, String description, String pic_url, int price, int store_id ) {
        this.antiqe_id = antiqe_id;
        this.name = name;
        this.description = description;
        this.pic_url = pic_url;
        this.price = price;
        this.store_id = store_id;
    }

    // GETTERS
    public int getAntiqe_id() {return antiqe_id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getPic_url() {return pic_url;}
    public int getPrice() {return price;}
    public int getStore_id() {return store_id;}

    // SETTERS
    public void setAntiqe_id(int antiqe_id) {this.antiqe_id = antiqe_id;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setPic_url(String pic_url) {this.pic_url = pic_url;}
    public void setPrice(int price) {this.price = price;}
    public void setStore_id(int store_id) {this.store_id = store_id;}

}
