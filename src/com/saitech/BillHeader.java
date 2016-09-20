package com.saitech;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class BillHeader {
	
	String shopName;
    String city;
    String district;
    String state;
    
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    long bill_num;
    float bill_amt;
    int total_qty;
    int total_item;


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getBill_num() {
        return bill_num;
    }

    public void setBill_num(long bill_num) {
        this.bill_num = bill_num;
    }

    public float getBill_amt() {
        return bill_amt;
    }

    public void setBill_amt(float bill_amt) {
        this.bill_amt = bill_amt;
    }

    public int getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(int total_qty) {
        this.total_qty = total_qty;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }
    

}
