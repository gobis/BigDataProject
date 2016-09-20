package com.saitech;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class BillDetail {
	
        private String itemDes;
	    private String productCode;
	    private double unitMRP;
	    private String manfUUID;
	    private int qty;
	    
	    @Id 
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private int _id;

	    public String getItemDes() {
	        return itemDes;
	    }

	    public void setItemDes(String itemDes) {
	        this.itemDes = itemDes;
	    }

	    public String getProductCode() {
	        return productCode;
	    }

	    public void setProductCode(String productCode) {
	        this.productCode = productCode;
	    }

	    public double getUnitMRP() {
	        return unitMRP;
	    }

	    public void setUnitMRP(double unitMRP) {
	        this.unitMRP = unitMRP;
	    }

	    public String getManfUUID() {
	        return manfUUID;
	    }

	    public void setManfUUID(String manfUUID) {
	        this.manfUUID = manfUUID;
	    }

	    public int getQty() {
	        return qty;
	    }

	    public void setQty(int qty) {
	        this.qty = qty;
	    }

	    

}
