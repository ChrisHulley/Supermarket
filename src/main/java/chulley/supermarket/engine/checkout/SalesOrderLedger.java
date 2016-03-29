/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.checkout;

// class imports
import chulley.supermarket.engine.pricing.SpecialOffers;
import chulley.supermarket.engine.pricing.StockCatalogue;
import java.util.HashMap;

/**
 * this class will hold the completed order history for everyone using the supermarket.
 * 
 * Each time person asks for a new id they will be given a new number and added to the stack.
 * They can add, update and delete entries from their own session id
 * 
 * @author Chris
 */
public class SalesOrderLedger {
    
    // static incrementing id and sales map
    private static final HashMap<Integer, CheckoutSession> salesLedger;
    private static int transactionID;
    static {
        
        // declare the map and initialise the id to one
        salesLedger = new HashMap<Integer, CheckoutSession>();
        transactionID = 1;
    }
    
    /**
     * generate a new checkout session and increment the id for future use. 
     * Also made synchronised to prevent double session id.  A copy of both the stock list
     * and the special items are created for the session in case they are later changed
     * @return
     */
    public static synchronized  int generateNewTransactionId() {
        salesLedger.put(transactionID, new CheckoutSession(transactionID, StockCatalogue.returnStockCatalogue(), SpecialOffers.returnSpecialOffers()));
        return transactionID++;
    }
    
    /**
     * return the customer session class
     * @param transactionID
     * @return
     */
    public static CheckoutSession returnCustomerSession(int transactionID) {
        return salesLedger.get(transactionID);
    }
    
    /**
     * Does the transaction id already exist in the ledger
     * @param transactionID
     * @return
     */
    public static boolean isSessionAvailable(int transactionID) {
        return salesLedger.containsKey(transactionID);
    }
    
    /**
     * update the customer's session contents returning the previous version
     * of the customer session before update
     * @param transactionID
     * @param checkOut
     * @return
     */
    public static CheckoutSession updateCustomerSession(int transactionID, CheckoutSession checkOut) {
        return salesLedger.put(transactionID, checkOut);
    }
    
}
