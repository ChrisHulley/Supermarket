/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chulley.supermarket.engine.pricing;

import chulley.supermarket.engine.pricing.items.StockUnitItem;
import java.util.HashMap;

/**
 * this static class will hold the full catalogue of stock.
 * 
 * The first time this class is loaded a default set of stock items will be 
 * added with their basic unit price set out in the table below
 * 
 * Item Unit Price
 * A    50
 * B    30
 * C    20
 * D    15
 * 
 * @author Chris
 */
public class StockCatalogue {
    
    // static holder of all pricing catalog items
    private static final HashMap<String, StockUnitItem> shopCatalog;
    
    // static initialiser to fire in the basic stock
    static {
        
        // generate a new hashmap
        shopCatalog = new HashMap<String, StockUnitItem>();
        
        // default set of stock items
        shopCatalog.put("A", new StockUnitItem("A", 50));
        shopCatalog.put("B", new StockUnitItem("B", 30));
        shopCatalog.put("C", new StockUnitItem("C", 20));
        shopCatalog.put("D", new StockUnitItem("D", 15));
    }
    
    /**
     * simply return the catalogue 
     * @return
     */
    public static HashMap<String, StockUnitItem> returnStockCatalogue() {
        return shopCatalog;
    }
    
    /**
     * add/update a stock item entry for the given ID
     * @param newEntry
     * @return
     */
    public static StockUnitItem putStockEntry(StockUnitItem newEntry) {
        return shopCatalog.put(newEntry.getItemID(), newEntry);
    }
    
    /**
     * delete the stock item with the passed ID
     * @param stockID
     * @return
     */
    public static StockUnitItem deleteStockEntry(String stockID) {
        return shopCatalog.remove(stockID);
    }
}
