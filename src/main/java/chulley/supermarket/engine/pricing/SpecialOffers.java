/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.pricing;

import chulley.supermarket.engine.pricing.items.MultibuyItem;
import java.util.HashMap;

/**
 * this static class will hold the special offers by stock id
 * 
 * The first time this class is loaded the default special offers
 * will be loaded into the hashmap
 * 
 * Item     Special Offer
 * A        3 for 130
 * B        2 for 45
 * 
 * @author Chris
 */
public class SpecialOffers {

    // static holder of all special offers
    private static final HashMap<String, MultibuyItem> specialOffers;    

    // static initialiser to fire in the default offers
    static {
        
        // generate a new hashmap
        specialOffers = new HashMap<String, MultibuyItem>();
        
        // default set of stock items
        specialOffers.put("A", new MultibuyItem("A", 3, 130));
        specialOffers.put("B", new MultibuyItem("B", 2, 45));
    }
    
    /**
     * simply return the special offers
     * @return
     */
    public static HashMap<String, MultibuyItem> returnSpecialOffers() {
        return specialOffers;
    }
    
    /**
     * add/update an item entry for the given ID
     * @param newEntry
     * @return
     */
    public static MultibuyItem putSpecialOffer(MultibuyItem newEntry) {
        return specialOffers.put(newEntry.getItemID(), newEntry);
    }
    
    /**
     * delete the special offers with the passed ID
     * @param stockID
     * @return
     */
    public static MultibuyItem deleteSpecialOffer(String stockID) {
        return specialOffers.remove(stockID);
    }   
}
