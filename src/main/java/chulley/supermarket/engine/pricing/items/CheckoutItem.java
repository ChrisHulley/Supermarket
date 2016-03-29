/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.pricing.items;

/**
 * extending the basic stock item object for checkout activities
 * 
 * @author Chris
 */
public class CheckoutItem extends StockUnitItem {
    
    // class variables
    private int itemQuantity;
    private int runningTotal;
    
    /**
     * overloaded constructor to initialise the class items
     * 
     * @param itemID
     * @param unitPrice
     * @param purchaseQty
     */
    public CheckoutItem(String itemID, int unitPrice, int purchaseQty) {
        super(itemID, unitPrice);
        
        // set the purchase qty
        this.itemQuantity = purchaseQty;
        
        // update the running total
        updateRunningTotal();        
    }

    /**
     * @return the itemQuantity
     */
    public int getItemQuantity() {
        return itemQuantity;
    }

    /**
     * @param itemQuantity the itemQuantity to set
     */
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
        
        // update the running total
        updateRunningTotal();
    }
    
    /**
     * increment the quantity
     */
    public void incItemQuantity() {
        this.itemQuantity++;
        
        // update the running total
        updateRunningTotal();
    }
    
    /**
     * decrease the quantity
     */
    public void decItemQuantity() {
        this.itemQuantity--;
        
        // update the running total
        updateRunningTotal();
    }

    /**
     * @return the runningTotal
     */
    public int getRunningTotal() {
        return runningTotal;
    }   
    
    /* utility method to update the running total */
    private void updateRunningTotal() {
        this.runningTotal = (getItemQuantity() * getUnitPrice());
    }
    
    @Override
    public String toString() {
        return String.format("%d * '%s' @ %d\t= %d", this.getItemQuantity(), this.getItemID(), this.getUnitPrice(), this.getRunningTotal());
    }    
}
