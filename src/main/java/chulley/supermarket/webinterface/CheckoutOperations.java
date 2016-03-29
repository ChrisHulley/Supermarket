/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.webinterface;

// class imports
import chulley.supermarket.engine.checkout.CheckoutSession;
import chulley.supermarket.engine.checkout.SalesOrderLedger;
import chulley.supermarket.engine.pricing.SpecialOffers;
import chulley.supermarket.engine.pricing.StockCatalogue;
import chulley.supermarket.engine.pricing.items.MultibuyItem;
import chulley.supermarket.engine.pricing.items.StockUnitItem;
import chulley.supermarket.exceptions.EXInvalidTransactionID;
import chulley.supermarket.exceptions.EXInvalidStockID;
import chulley.supermarket.exceptions.EXInvalidItemValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// spring integration
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * this is the main interface for all web interface transactions that pertain to
 * the sales and checking out of items.
 * 
 * @author Chris
 */
@RestController
public class CheckoutOperations {
    
    /**
     * does the transactionID already have an active session
     * @param transactionID
     * @return
     */
    @RequestMapping("/IsSessionAlive")
    public boolean isSessionAvailable(@RequestParam(value = "TransID") int transactionID) {
        return SalesOrderLedger.isSessionAvailable(transactionID);
    }
    
    /**
     * create a new session and return the transaction id
     * @return
     */
    @RequestMapping("/NewSession")
    public int createNewCheckoutSession() {
        return SalesOrderLedger.generateNewTransactionId();
    }
    
    /**
     * return the stock catalogue for the current session
     * 
     * If the transaction does not exist the current stock catalogue values will
     * be returned instead
     * @param transactionID
     * @return
     */
    @RequestMapping("/Catalogue")
    public Collection<StockUnitItem> returnCatalogueContents(@RequestParam(value = "TransID") int transactionID) {
        
        // if the transaction id exists return the session's stock list, otherwise return the current
        return (SalesOrderLedger.isSessionAvailable(transactionID))? SalesOrderLedger.returnCustomerSession(transactionID).getStockCatalogue().values() : StockCatalogue.returnStockCatalogue().values();
    }

    /**
     * return the special offers
     * @param transactionID
     * @return
     */
    @RequestMapping("/SpecialOffers")
    public Collection<MultibuyItem> returnSpecialOffers(@RequestParam(value = "TransID") int transactionID) {
        return (SalesOrderLedger.isSessionAvailable(transactionID))? SalesOrderLedger.returnCustomerSession(transactionID).getSpecialOffers().values() : SpecialOffers.returnSpecialOffers().values();
    }

    
    /**
     * add a new stock item to the checkout list for the passed transactionID
     * @param transactionID
     * @param stockID
     * @throws chulley.supermarket.exceptions.EXInvalidTransactionID
     * @throws chulley.supermarket.exceptions.EXInvalidStockID
     */
    @RequestMapping("/AddNewCOItem")
    public void addNewCheckoutItem(@RequestParam(value = "TransID")int transactionID, @RequestParam(value = "StockID") String stockID) throws EXInvalidTransactionID, EXInvalidStockID {
        
        // cannot add if it doesn't exist
        if (!SalesOrderLedger.isSessionAvailable(transactionID)) {
            throw new EXInvalidTransactionID(transactionID);
        }
        
        // the stock id should also exist for the transaction for the checkout session
        if (!SalesOrderLedger.returnCustomerSession(transactionID).getStockCatalogue().containsKey(stockID)) {
            throw new EXInvalidStockID(stockID);
        }
        
        // the session exists, capture the CheckoutSession add the item to the current session
        SalesOrderLedger.returnCustomerSession(transactionID).addItem(new StockUnitItem(stockID, StockCatalogue.returnStockCatalogue().get(stockID).getUnitPrice()));
    }
    
    /**
     * remove an item from the transaction stack
     * @param transactionID
     * @param stockID
     * @throws EXInvalidTransactionID
     * @throws EXInvalidStockID
     */
    @RequestMapping("/RemoveCOItem")
    public void removeCheckoutItem(@RequestParam(value = "TransID")int transactionID, @RequestParam(value = "StockID") String stockID) throws EXInvalidTransactionID, EXInvalidStockID {
        
        // cannot add if it doesn't exist
        if (!SalesOrderLedger.isSessionAvailable(transactionID)) {
            throw new EXInvalidTransactionID(transactionID);
        }
        
        // the stock id should also exist
        if (!SalesOrderLedger.returnCustomerSession(transactionID).getStockCatalogue().containsKey(stockID)) {
            throw new EXInvalidStockID(stockID);
        }
        
        // the session exists, capture the CheckoutSession and remove the item from the session
        SalesOrderLedger.returnCustomerSession(transactionID).removeItem(StockCatalogue.returnStockCatalogue().get(stockID));
    }  
    
    /**
     * add a new item to the stock catalogue
     * @param stockID
     * @param stockValue
     * @throws chulley.supermarket.exceptions.EXInvalidItemValue
     */
    @RequestMapping("/AddNewStockItem")
    public void addNewStockItem(@RequestParam(value = "StockID") String stockID, @RequestParam(value = "StockValue") String stockValue) throws EXInvalidItemValue {
        
        // local variable for stock value
        int intValue = 0;
        
        // the stock value must be numeric or throw an error
        try {
            intValue = Integer.parseInt(stockValue);
        } catch (NumberFormatException ex) {
            throw new EXInvalidItemValue("Stock", "value", stockID);
        }
        
        // add a new stock item to the stack
        StockCatalogue.putStockEntry(new StockUnitItem(stockID, intValue));
    } 
    
    /**
     * remove an item from the stock catalogue
     * @param stockID
     * @throws EXInvalidStockID
     */
    @RequestMapping("/RemoveStockItem")
    public void removeStockItem(@RequestParam(value = "StockID") String stockID) throws EXInvalidStockID {
        
        // the stock id should also exist
        if (!StockCatalogue.returnStockCatalogue().containsKey(stockID)) {
            throw new EXInvalidStockID(stockID);
        }
        
        // remove the stock item from the main stock catalogue
        StockCatalogue.deleteStockEntry(stockID);
    } 
    
    /**
     * remove an item from the special offers
     * @param stockID
     * @throws EXInvalidStockID
     */
    @RequestMapping("/RemoveSpecialOffer")
    public void removeSpecialOffer(@RequestParam(value = "StockID") String stockID) throws EXInvalidStockID {
        
        // the stock id should also exist in the special offer list
        if (!SpecialOffers.returnSpecialOffers().containsKey(stockID)) {
            throw new EXInvalidStockID(stockID);
        }
        
        // remove the special offer from the list
        SpecialOffers.deleteSpecialOffer(stockID);
    } 
    
    /**
     * remove an item from the special offers
     * @param specialID
     * @param specialQty
     * @param specialValue
     * @throws chulley.supermarket.exceptions.EXInvalidItemValue
     */
    @RequestMapping("/AddSpecialOffer")
    public void addSpecialOffer(@RequestParam(value = "SpecialID") String specialID, @RequestParam(value = "SpecialQty") String specialQty, @RequestParam(value = "SpecialValue") String specialValue) throws EXInvalidItemValue {
        
        // variables for special value and quantity
        int intQuantity = 0;
        int intValue = 0;
        
        // the special quantity must be numeric or throw an error
        try {
            intQuantity = Integer.parseInt(specialQty);
        } catch (NumberFormatException ex) {
            throw new EXInvalidItemValue("Special", "quantity", specialQty);
        }
        
        // the special value must be numeric or throw an error
        try {
            intValue = Integer.parseInt(specialValue);
        } catch (NumberFormatException ex) {
            throw new EXInvalidItemValue("Special", "value", specialValue);
        }
        
        // add/update a new special item to the main catalogue
        SpecialOffers.putSpecialOffer(new MultibuyItem(specialID, intQuantity, intValue));
    } 
    
    /**
     * Output the till roll with all the items in the shopping list and the price
     * @param transactionID
     * @return
     * @throws EXInvalidTransactionID
     */
    @RequestMapping("/TillRoll")
    public List<String> generateTillRoll(@RequestParam(value = "TransID") int transactionID) throws EXInvalidTransactionID {
        
        // cannot generate receipt if it doesn't exist
        if (!SalesOrderLedger.isSessionAvailable(transactionID)) {
            throw new EXInvalidTransactionID(transactionID);
        }
        
        // get the session object into memory
        CheckoutSession currentSession = SalesOrderLedger.returnCustomerSession(transactionID);
        
        // init 2 lists for the output
        List<String> tillRoll = new ArrayList<String>();
        List<String> multiBuy = new ArrayList<String>();
        boolean bMultiBuy = false;
        
        // initialise the return string and multi-buy section
        multiBuy.add("*************************************************");
        multiBuy.add("**----------------MULTI-BUYS-------------------**");
        multiBuy.add("*************************************************");
        
        // init the till roll
        tillRoll.add("*************************************************");
        tillRoll.add("**-----------SUPERMARKET RECEIPT---------------**");
        tillRoll.add("*************************************************");
        tillRoll.add("transaction id " + currentSession.getSessionID());
        tillRoll.add("@ " + currentSession.getCheckoutDate().toString());
        
        // for each item in the array 
        for (String currentKey : currentSession.getCheckoutList().keySet()) {
            
            // append to the till roll string
            tillRoll.add(currentSession.getCheckoutList().get(currentKey).toString());
            
            // append to the multi-buy string
            if (currentSession.returnMultibuyString(currentKey).length() > 0) {
                multiBuy.add(currentSession.returnMultibuyString(currentKey));
                bMultiBuy = true;
            }
        }
        
        // add the multi-buy section if there are any
        if (bMultiBuy) {
            tillRoll.addAll(multiBuy);
        }
        
        // finish with the total
        tillRoll.add("*************************************************");
        tillRoll.add(String.format("Total\t= %d", currentSession.getRunningTotal()));
        
        // all done
        return tillRoll;
    }
    
}
