/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chulley.supermarket.engine.pricing.items;

// class imports
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * test an item of "A" and price 9
 * @author Chris
 */
public class StockUnitItemTest {
    
    // private member item
    private final StockUnitItem stockItem;
    
    // constructor
    public StockUnitItemTest() {
        
        // initialise to have Item A, Price 9
        stockItem = new StockUnitItem("A", 9);
    }

    /**
     * Test of getItemID method, of class StockUnitItem.
     */
    @Test
    public void testGetItemID() {
        System.out.println(String.format("getItemID :: expecting 'A', got '%s'", stockItem.getItemID()));
        
        // test the result
        assertEquals("A", stockItem.getItemID());
    }

    /**
     * Test of getUnitPrice method, of class StockUnitItem.
     */
    @Test
    public void testGetUnitPrice() {
        System.out.println(String.format("getUnitPrice :: expecting 9, got %d", stockItem.getUnitPrice()));

        // test the result
        assertEquals(9, stockItem.getUnitPrice());
    }
    
}
