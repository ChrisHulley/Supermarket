/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.checkout;

// class imports
import chulley.supermarket.engine.pricing.items.CheckoutItem;
import chulley.supermarket.engine.pricing.items.MultibuyItem;
import chulley.supermarket.engine.pricing.items.StockUnitItem;
import java.util.Date;
import java.util.HashMap;

/**
 * this will hold all the information for a checkout session
 * 
 * Currently this will include all the items purchased and the time it was done
 * @author Chris
 */
public class CheckoutSession {
    
    // private member variables
    private final HashMap<String, StockUnitItem> stockCatalogue;
    private final HashMap<String, MultibuyItem> specialOffers;
    private final HashMap<String, CheckoutItem> checkoutList;    
    private final Date checkoutDate;
    private final int sessionID;    
    
    /**
     * constructor with the session id; also creates the date
     * @param sessionID
     * @param stockList
     * @param specialOffers
     */
    public CheckoutSession(int sessionID, HashMap<String, StockUnitItem> stockList, HashMap<String, MultibuyItem> specialOffers) {
        
        // initialise the 2 final items for the class
        this.checkoutList = new HashMap<String, CheckoutItem>();
        this.checkoutDate = new Date();
        this.sessionID = sessionID;
        
        // save the current stock list and specials
        this.specialOffers = new HashMap<String, MultibuyItem>(specialOffers);
        this.stockCatalogue = new HashMap<String, StockUnitItem>(stockList);
    }

    /**
     * Add a new item to the checkout list.  
     * Returns the updated CheckoutItem class object after the update
     * @param newItem
     * @return
     */
    public CheckoutItem addItem(StockUnitItem newItem) {
        
        // get the checkout item from the stack or create a new one
        CheckoutItem addItem = (getCheckoutList().containsKey(newItem.getItemID())) ? getCheckoutList().get(newItem.getItemID()) : new CheckoutItem(newItem.getItemID(), newItem.getUnitPrice(), 0);
        
        // increment the quantity of the item
        addItem.incItemQuantity();
        
        // add the updated item to the stack and return
        getCheckoutList().put(addItem.getItemID(), addItem);
        return addItem;
    }
    
    /**
     * Removes a checkout item from the list.
     * Returns the CheckoutItem that was removed or null if 
     * the object does not exist in the checkout list 
     * @param removeItem
     * @return
     */
    public CheckoutItem removeItem(StockUnitItem removeItem) {
        
        // only continue if the stock item exists for the session
        if (!checkoutList.containsKey(removeItem.getItemID())) {
            return null;
        }
        
        // the item exists, get the current values and decrease quantity
        CheckoutItem checkItem = getCheckoutList().get(removeItem.getItemID());
        checkItem.decItemQuantity();
        
        // write back to the stack
        getCheckoutList().put(checkItem.getItemID(), checkItem);
        
        // if the quantity is <= 0 then remove it and send back the modified item that was removed
        return (checkItem.getItemQuantity() <= 0) ? getCheckoutList().remove(removeItem.getItemID()) : checkItem;
    }
    
    /**
     * does the itemID exist in the checkout list
     * @param itemID
     * @return
     */
    public boolean itemExists(String itemID) {
        return getCheckoutList().containsKey(itemID);
    }
    
    /**
     * return the CheckoutItem from the stack
     * @param itemID
     * @return
     */
    public CheckoutItem getItem(String itemID) {
        return getCheckoutList().get(itemID);
    }
    
    /**
     * Returns the basket total for the session.  This includes any discounts
     * from multi-buy stock items
     * @return
     */
    public int getRunningTotal() {
        
        // init running total and savings
        int runningTotal = 0;
        
        // spin through the list and calculate the totals
        for (String currentKey : getCheckoutList().keySet()) {

            // increment the total less any discounts
            runningTotal += getCheckoutList().get(currentKey).getRunningTotal() - calculateDiscount(currentKey);

        }
        
        // all done
        return runningTotal;
    }
    
    /**
     * simply clear the basket contents
     */
    public void clearBasket() {
        getCheckoutList().clear();
    }

    /**
     * @return the checkoutDate
     */
    public Date getCheckoutDate() {
        return checkoutDate;
    }

    /**
     * @return the sessionID
     */
    public int getSessionID() {
        return sessionID;
    }
    
    @Override
    public String toString() {
        return String.format("CheckoutSession :: ID %d @ %s", getSessionID(), getCheckoutDate());
    }
    
    
    /* calculate any multi-buy discounts */
    private int calculateDiscount(String itemID) {
        
        // init discount value
        int discountValue = 0;

        // is the item on special offer??
        if (getSpecialOffers().containsKey(itemID)) {

            // only if qualifying for a multibuy discount
            if (getSpecialOffers().get(itemID).getMultiBuyQty() <= getCheckoutList().get(itemID).getItemQuantity()) {
                
                // work out how many multiples of discount and how much they would have costed
                int multiBuys  = (int)Math.floor((getCheckoutList().get(itemID).getItemQuantity() / getSpecialOffers().get(itemID).getMultiBuyQty()));             
                int totalPrice = ((multiBuys * getSpecialOffers().get(itemID).getMultiBuyQty()) * getCheckoutList().get(itemID).getUnitPrice());

                // calculate discount using the full price total and multiples of the multibuy price
                discountValue = totalPrice - (getSpecialOffers().get(itemID).getMultiBuyPrice() * multiBuys);
            }
        }
        
        // return discount
        return discountValue;
    }     
    
    /**
     * return the formatted multi-buy string
     * @param itemID
     * @return
     */
    public String returnMultibuyString(String itemID) {
        
        // init return value
        String multiBuy = "";

        // is the item on special offer??
        if (getSpecialOffers().containsKey(itemID)) {

            // only if qualifying for a multibuy discount
            if (getSpecialOffers().get(itemID).getMultiBuyQty() <= getCheckoutList().get(itemID).getItemQuantity()) {
                
                // work out how many multiples of discount and how much they would have costed
                int multiBuys  = (int)Math.floor((getCheckoutList().get(itemID).getItemQuantity() / getSpecialOffers().get(itemID).getMultiBuyQty()));             
                int totalPrice = ((multiBuys * getSpecialOffers().get(itemID).getMultiBuyQty()) * getCheckoutList().get(itemID).getUnitPrice());

                // calculate discount using the full price total and multiples of the multibuy price
                int discountValue = totalPrice - (getSpecialOffers().get(itemID).getMultiBuyPrice() * multiBuys);
                
                // create the output string
                multiBuy = String.format("%d x Multi-buy discount on '%s' = -%d", multiBuys, itemID, discountValue);
            }
        }
        
        // return string
        return multiBuy;
    }

    /**
     * @return the checkoutList
     */
    public HashMap<String, CheckoutItem> getCheckoutList() {
        return checkoutList;
    }    

    /**
     * @return the stockCatalogue
     */
    public HashMap<String, StockUnitItem> getStockCatalogue() {
        return stockCatalogue;
    }

    /**
     * @return the specialOffers
     */
    public HashMap<String, MultibuyItem> getSpecialOffers() {
        return specialOffers;
    }
}
