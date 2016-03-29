/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chulley.supermarket.engine.pricing.items;

/**
 * A simply class to encapsulate the properties of a stock item.  
 * 
 * In a normal supermarket, things are identified using Stock Keeping Units, or SKUs. 
 * In our store, we will use individual letters of the alphabet (A, B, C, and so on as the SKUs). 
 * Our goods are priced individually
 * 
 * ** NOTE ** 
 * for the purpose of this exercise the 'unitPrice' is an integer.  Normally it would be 
 * a double to 2 decimal places but here it is just an integer.
 * 
 * @author Chris
 */
public class StockUnitItem extends BaseItem {
    
    // private member variables
    private int unitPrice;
    
    /**
     * overloaded constructor that also sets the values
     * @param itemID
     * @param unitPrice
     */
    public StockUnitItem(String itemID, int unitPrice) {
        super(itemID);
        
        // set the unit price
        this.unitPrice = unitPrice;
    }

    /**
     * @return the unitPrice
     */
    public int getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    @Override
    public String toString() {
        return String.format("StockUnitItem :: Item '%s', Unit Price %d", this.getItemID(), this.getUnitPrice());
    }
}
