/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.checkout;

// class imports
import chulley.supermarket.engine.pricing.SpecialOffers;
import chulley.supermarket.engine.pricing.items.CheckoutItem;
import chulley.supermarket.engine.pricing.StockCatalogue;
import chulley.supermarket.engine.pricing.items.StockUnitItem;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author hulleyc
 */
public class CheckoutSessionTest {
    
    // private member for CheckoutSession
    private final CheckoutSession checkOut;
    
    // constructor
    public CheckoutSessionTest() {
        
        // create a new session with id 1
        checkOut = new CheckoutSession(1, StockCatalogue.returnStockCatalogue(), SpecialOffers.returnSpecialOffers());
    }

    /**
     * Test adding an entry to the map
     */
    @Test
    public void testAddItem() {
        System.out.println("testAddItem");
        
        // add a new entry to the list
        CheckoutItem result = checkOut.addItem(new StockUnitItem("A", 9));
        System.out.println(String.format("Added %s", result.toString()));      
        
        // check the actual key is there
        assertTrue(checkOut.itemExists("A"));
        System.out.println(String.format("Get Item 'A' from stack :: %s", checkOut.getItem("A").toString()));
    }
    
    
    /**
     * Test of removeItem method, of class CheckoutSession.
     */
    @Test
    public void testRemoveItem() {
        System.out.println("removeItem");
        
        // add a new entry to the list
        CheckoutItem result = checkOut.addItem(new StockUnitItem("Z", 99));
        System.out.println(String.format("Added %s", result.toString()));
        
        // now delete it!!
        checkOut.removeItem(new StockUnitItem("Z", 99));
        
        // check the actual key is no longer there
        assertFalse(checkOut.itemExists("Z"));
        System.out.println("Item 'Z' has been removed from stack");
    }

    /**
     * Test of getCheckoutDate method, of class CheckoutSession.
     */
    @Test
    public void testGetCheckoutDate() {
        System.out.println(String.format("getCheckoutDate :: created @ %s", checkOut.getCheckoutDate().toString()));
    }

    /**
     * Test of getSessionID method, of class CheckoutSession.
     */
    @Test
    public void testGetSessionID() {
        System.out.println(String.format("getSessionID :: expected 1, got %d", checkOut.getSessionID()));
        
        // test result
        assertEquals(1, checkOut.getSessionID());
    }

    /**
     * Test of getRunningTotal method, of class CheckoutSession.
     */
    @Test
    public void testGetRunningTotal() {
        System.out.println("getRunningTotal for the checkout session");
        
        // clear the basket of any goods
        checkOut.clearBasket();
        
        // add 3 separate items from the catalogue A (x4), B and C
        checkOut.addItem(StockCatalogue.returnStockCatalogue().get("A"));
        checkOut.addItem(StockCatalogue.returnStockCatalogue().get("B"));
        checkOut.addItem(StockCatalogue.returnStockCatalogue().get("A"));
        checkOut.addItem(StockCatalogue.returnStockCatalogue().get("A"));
        checkOut.addItem(StockCatalogue.returnStockCatalogue().get("C"));
        checkOut.addItem(StockCatalogue.returnStockCatalogue().get("A"));
        
        // 4xA = 200 minus 20 for 1xmultibuy,  1xB = 30,  1xC = 20
        assertEquals(230, checkOut.getRunningTotal());
    }

    /**
     * Test of itemExists method, of class CheckoutSession.
     */
    @Test
    public void testItemExists() {
        System.out.println("testItemExists for the checkout session");
        
        // add T to the basket
        checkOut.addItem(new StockUnitItem("T", 10));
        
        // does t exist?
        assertTrue(checkOut.itemExists("T"));
    }

    /**
     * Test of getItem method, of class CheckoutSession.
     */
    @Test
    public void testGetItem() {
        System.out.println("testGetItem for the checkout session");
        
        // declare new Checkout item 3 x K @ 25
        CheckoutItem newItem = new CheckoutItem("K", 25, 3);
        checkOut.addItem(new StockUnitItem(newItem.getItemID(), newItem.getUnitPrice()));
        checkOut.addItem(new StockUnitItem(newItem.getItemID(), newItem.getUnitPrice()));
        checkOut.addItem(new StockUnitItem(newItem.getItemID(), newItem.getUnitPrice()));
        
        // now get the item
        System.out.println("SRC : " + newItem.toString());
        System.out.println("NEW : " + checkOut.getItem("K").toString());
        assertEquals(newItem.toString(), checkOut.getItem("K").toString());
    }

    /**
     * Test of clearBasket method, of class CheckoutSession.
     */
    @Test
    public void testClearBasket() {
        System.out.println("testClearBasket for the checkout session");
        
        // clear the basket
        checkOut.clearBasket();
        
        // test the size of the basket
        assertEquals(0, checkOut.getCheckoutList().size());
    }
    
}
