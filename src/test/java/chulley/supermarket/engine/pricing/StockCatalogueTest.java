/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chulley.supermarket.engine.pricing;

// class imports
import chulley.supermarket.engine.pricing.items.StockUnitItem;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * manipulate the static class catalogue
 * @author Chris
 */
public class StockCatalogueTest {

    /**
     * Test of returnStockCatalogue method
     */
    @Test
    public void testReturnStockCatalogue() {
        System.out.println("returnStockCatalogue, to just output the hashmap");
        
        // write out the contents of the hashmap
        for (String key : StockCatalogue.returnStockCatalogue().keySet()) {
            System.out.println(StockCatalogue.returnStockCatalogue().get(key).toString());
        }
    }

    /**
     * Test of putStockEntry method, of class StockCatalogue.
     */
    @Test
    public void testPutStockEntry() {
        
        // create the new unit
        StockUnitItem newUnit = new StockUnitItem("X", 99);
        
        // start test
        System.out.println(String.format("putStockEntry :: %s", newUnit.toString()));
        
        // add to the stack - should always return NULL for the first addition
        assertEquals(null, StockCatalogue.putStockEntry(newUnit));
        
        // test it is present
        assertTrue(StockCatalogue.returnStockCatalogue().containsValue(newUnit));
    }

    /**
     * Test of deleteStockEntry method, of class StockCatalogue.
     */
    @Test
    public void testDeleteStockEntry() {
        
        // will delete 'D' from the catalog (will always be there at startup)
        System.out.println(String.format("deleteStockEntry :: %s", StockCatalogue.returnStockCatalogue().get("D").toString()));
        
        // capture the deleted item
        StockUnitItem result = StockCatalogue.deleteStockEntry("D");
        System.out.println(String.format("DELETED %s", result.toString()));
        
        // test it is no longer present
        assertFalse(StockCatalogue.returnStockCatalogue().containsKey("D"));
    }
    
}
