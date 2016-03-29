/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.pricing.items;

/**
 * the most basic properties that are common to all type of item
 * 
 * @author Chris
 */
public class BaseItem {
    
    // private members
    private String itemID;
    
    /**
     * overloaded constructor that also sets the values
     * @param itemID
     */
    public BaseItem(String itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the itemID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }    
}
