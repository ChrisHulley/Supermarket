/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.pricing.items;

/**
 * A simply class to encapsulate the properties of a multi-buy item.  
 * 
 * In addition, some items are multi-priced: buy n of them, and they'll cost you y. 
 * For example, item 'A' might cost 50 pence individually, but this week we have a special offer.
 * 
 * Buy three 'A's and they'll cost you �1.30.
 * 
 * ** NOTE ** 
 * for the purpose of this exercise the 'multiBuyPrice' is an integer.  Normally it would be 
 * a double to 2 decimal places but here it is just an integer.
 * 
 * @author Chris
 */
public class MultibuyItem extends BaseItem {
    
    // private member variables
    private int multiBuyQty;
    private int multiBuyPrice;
    
    /**
     * overloaded constructor to initialise on create
     * @param itemID
     * @param itemQuantity
     * @param specialPrice
     */
    public MultibuyItem(String itemID, int itemQuantity, int specialPrice) {
        super(itemID);
        
        // set multi-buy items
        this.multiBuyPrice = specialPrice;
        this.multiBuyQty = itemQuantity;         
    }

    /**
     * @return the multiBuyQty
     */
    public int getMultiBuyQty() {
        return multiBuyQty;
    }

    /**
     * @param multiBuyQty the multiBuyQty to set
     */
    public void setMultiBuyQty(int multiBuyQty) {
        this.multiBuyQty = multiBuyQty;
    }

    /**
     * @return the multiBuyPrice
     */
    public int getMultiBuyPrice() {
        return multiBuyPrice;
    }

    /**
     * @param multiBuyPrice the multiBuyPrice to set
     */
    public void setMultiBuyPrice(int multiBuyPrice) {
        this.multiBuyPrice = multiBuyPrice;
    }
    
    @Override
    public String toString() {
        return String.format("MultibuyItem :: Item '%s', Qty %d, Special Price %d", this.getItemID(), this.getMultiBuyQty(), this.getMultiBuyPrice());
    }    
}
