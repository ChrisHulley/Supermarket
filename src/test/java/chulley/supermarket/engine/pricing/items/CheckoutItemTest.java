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
 *
 * @author hulleyc
 */
public class CheckoutItemTest {
    
    // private version of the item
    private final CheckoutItem checkItem;
    
    // constructor
    public CheckoutItemTest() {
        
        // initialise to use A, unit price 9 and 2 items purchased
        checkItem = new CheckoutItem("A", 9, 2);
    }

    /**
     * Test of getItemQuantity method, of class CheckoutItem.
     */
    @Test
    public void testGetItemQuantity() {
        // set the item quantity
        checkItem.setItemQuantity(2);
        
        // detail test
        System.out.println(String.format("getItemQuantity :: expecting 2, got %d", checkItem.getItemQuantity())); 
        
        // test the result
        assertEquals(2, checkItem.getItemQuantity());
    }

    /**
     * Test of getItemID method, of class CheckoutItem (inherited).
     */
    @Test
    public void testGetItemName() {
        System.out.println(String.format("getItemID :: expecting 'A', got '%s'", checkItem.getItemID()));
        
        // test the result
        assertEquals("A", checkItem.getItemID());
    }

    /**
     * Test of getUnitPrice method, of class CheckoutItem (inherited).
     */
    @Test
    public void testGetUnitPrice() {
        System.out.println(String.format("getUnitPrice :: expecting 9, got %d", checkItem.getUnitPrice()));

        // test the result
        assertEquals(9, checkItem.getUnitPrice());
    }    

    /**
     * Test of incItemQuantity method, of class CheckoutItem.
     */
    @Test
    public void testIncItemQuantity() {
        
        // get the current quantity and increment
        int startQty = checkItem.getItemQuantity();
        checkItem.incItemQuantity();
        
        // test the output 
        System.out.println(String.format("incItemQuantity :: start %d, end %d", startQty, checkItem.getItemQuantity()));
        assertEquals((startQty+1), checkItem.getItemQuantity());
    }

    /**
     * Test of decItemQuantity method, of class CheckoutItem.
     */
    @Test
    public void testDecItemQuantity() {
        
        // get the current quantity and decrease
        int startQty = checkItem.getItemQuantity();
        checkItem.decItemQuantity();
        
        // test the output 
        System.out.println(String.format("decItemQuantity :: start %d, end %d", startQty, checkItem.getItemQuantity()));
        assertEquals((startQty-1), checkItem.getItemQuantity());
    }

    /**
     * Test of getRunningTotal method, of class CheckoutItem.
     */
    @Test
    public void testGetRunningTotal() {
        System.out.println(String.format("getRunningTotal :: expecting %d, calculated %d", (checkItem.getItemQuantity() * checkItem.getUnitPrice()), checkItem.getRunningTotal()));
        
        // test the 2 figures are the same
        assertEquals((checkItem.getItemQuantity() * checkItem.getUnitPrice()), checkItem.getRunningTotal());
    }

    /**
     * Test of toString method, of class CheckoutItem.
     */
    @Test
    public void testToString() {
        System.out.println(String.format("toString -->%s", checkItem.toString()));
    }
    
}
