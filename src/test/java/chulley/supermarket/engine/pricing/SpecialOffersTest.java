/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.pricing;

// class imports
import chulley.supermarket.engine.pricing.items.MultibuyItem;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hulleyc
 */
public class SpecialOffersTest {

    /**
     * Test of returnSpecialOffers method, of class SpecialOffers.
     */
    @Test
    public void testReturnSpecialOffers() {
        System.out.println("testReturnSpecialOffers, to just output the hashmap");
        
        // write out the contents of the hashmap
        for (String key : SpecialOffers.returnSpecialOffers().keySet()) {
            System.out.println(SpecialOffers.returnSpecialOffers().get(key).toString());
        }
    }

    /**
     * Test of putSpecialOffer method, of class SpecialOffers.
     */
    @Test
    public void testPutSpecialOffer() {
        
        // create the new unit
        MultibuyItem newUnit = new MultibuyItem("Z", 3, 99);
        
        // start test
        System.out.println(String.format("testPutSpecialOffer :: %s", newUnit.toString()));
        
        // add to the stack - should always return NULL for the first addition
        assertEquals(null, SpecialOffers.putSpecialOffer(newUnit));
        
        // test it is present
        assertTrue(SpecialOffers.returnSpecialOffers().containsValue(newUnit));
    }

    /**
     * Test of deleteSpecialOffer method, of class SpecialOffers.
     */
    @Test
    public void testDeleteSpecialOffer() {
        
        // will delete 'A' from the catalog (will always be there at startup)
        System.out.println(String.format("testDeleteSpecialOffer :: %s", SpecialOffers.returnSpecialOffers().get("A").toString()));
        
        // capture the deleted item
        MultibuyItem result = SpecialOffers.deleteSpecialOffer("A");
        System.out.println(String.format("DELETED %s", result.toString()));
        
        // test it is no longer present
        assertFalse(SpecialOffers.returnSpecialOffers().containsKey("A"));
    }
 
}
