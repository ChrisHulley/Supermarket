/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.pricing.items;

// class imports
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hulleyc
 */
public class MultibuyItemTest {
    
    // private item
    private final MultibuyItem multiItem;
    
    // constructor
    public MultibuyItemTest() {
        
        // initialise to item 'A', 3 for �15
        multiItem = new MultibuyItem("A", 3, 15);
    }

    /**
     * Test of getItemID method, of class MultibuyItem.
     */
    @Test
    public void testGetItemID() {
        System.out.println(String.format("getItemID :: expecting 'A', got '%s'", multiItem.getItemID()));
        
        // test the result 
        assertEquals("A", multiItem.getItemID());
    }

    /**
     * Test of getMultiBuyQty method, of class MultibuyItem.
     */
    @Test
    public void testGetMultiBuyQty() {
        System.out.println(String.format("getMultiBuyQty :: expecting 3, got %d", multiItem.getMultiBuyQty()));
        
        // test the result
        assertEquals(3, multiItem.getMultiBuyQty());
    }

    /**
     * Test of getMultiBuyPrice method, of class MultibuyItem.
     */
    @Test
    public void testGetMultiBuyPrice() {
        System.out.println(String.format("getMultiBuyPrice :: expecting 15, got %d", multiItem.getMultiBuyPrice()));
        
        // test the result
        assertEquals(15, multiItem.getMultiBuyPrice());
    }

}
