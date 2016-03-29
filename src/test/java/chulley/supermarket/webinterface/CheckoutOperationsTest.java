/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chulley.supermarket.webinterface;

// class imports
import chulley.supermarket.engine.checkout.SalesOrderLedger;
import chulley.supermarket.engine.pricing.SpecialOffers;
import chulley.supermarket.engine.pricing.StockCatalogue;
import chulley.supermarket.engine.pricing.items.MultibuyItem;
import chulley.supermarket.engine.pricing.items.StockUnitItem;
import chulley.supermarket.exceptions.EXInvalidItemValue;
import chulley.supermarket.exceptions.EXInvalidStockID;
import chulley.supermarket.exceptions.EXInvalidTransactionID;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Chris
 */
public class CheckoutOperationsTest {
    
    // class variables
    CheckoutOperations checkOperations;
    
    // constructor
    public CheckoutOperationsTest() {
        checkOperations = new CheckoutOperations();
    }

    /**
     * Test of isSessionAvailable method, of class CheckoutOperations.
     */
    @Test
    public void testIsSessionAvailable() {
        System.out.println("isSessionAvailable :: 99");

        // this session will not exist at startup
        assertEquals(false, checkOperations.isSessionAvailable(99));
    }

    /**
     * Test of createNewCheckoutSession method, of class CheckoutOperations.
     */
    @Test
    public void testCreateNewCheckoutSession() {
        
        // create a new session id and get the id
        int transID = checkOperations.createNewCheckoutSession();
        System.out.println(String.format("createNewCheckoutSession transaction id is %d", transID));
        
        // test that it exists in the stack
        assertEquals(true, SalesOrderLedger.isSessionAvailable(transID));
    }


    /**
     * Test of addNewCheckoutItem method, of class CheckoutOperations.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddNewCheckoutItem() throws Exception {
        
        // create a new session id and get the id
        int transID = checkOperations.createNewCheckoutSession();
        System.out.println(String.format("addNewCheckoutItem transaction id is %d", transID));
        
        // add an items to the stack
        checkOperations.addNewCheckoutItem(transID, "B");
        
        // test that it has 'B' in the list
        assertTrue(SalesOrderLedger.returnCustomerSession(transID).itemExists("B"));
    }

    /**
     * Test of addNewCheckoutItem EXCEPTION
     */
    @Test
    public void testAddNewItem_INVALID_TRANS() {
        System.out.println("testAddNewItem_INVALID_TRANS transaction id is 999");
        
        // add an items to the stack
        try {
            checkOperations.addNewCheckoutItem(999, "A");

            // if it gets here we have a problem
            fail("Must throw and error :: testAddNewItem_INVALID_TRANS transaction id 999");
            
        } catch (EXInvalidTransactionID ex) {
            System.out.println(String.format("CORRECTLY THROWN ERROR :: %s", ex.getMessage()));
            
        } catch (EXInvalidStockID ex) {
            fail("The stock ID is valid, this should not fail");
        }
    }

    /**
     * Test of addNewCheckoutItem EXCEPTION
     */
    @Test
    public void testAddNewItem_INVALID_STOCK() {
        System.out.println("testAddNewItem_INVALID_STOCK stock id is Z");
        
        // add an items to the stack
        try {
            
            // get new transid
            checkOperations.addNewCheckoutItem(checkOperations.createNewCheckoutSession(), "Z");

            // if it gets here we have a problem
            fail("Must throw and error :: testAddNewItem_INVALID_STOCK stock id is Z");
            
        } catch (EXInvalidTransactionID ex) {
            fail("The Transaction ID is valid, this should not fail");
            
        } catch (EXInvalidStockID ex) {
            System.out.println(String.format("CORRECTLY THROWN ERROR :: %s", ex.getMessage()));
            
        }
    }

    /**
     * Test of generateTillRoll method, of class CheckoutOperations.
     * @throws java.lang.Exception
     */
    @Test
    public void testGenerateTillRoll() throws Exception {
        System.out.println("generateTillRoll with 6 items");
        
        // get a new transaction
        int transID = checkOperations.createNewCheckoutSession();
        
        // add 3 separate items from the catalogue A (x4), B and C
        checkOperations.addNewCheckoutItem(transID, "A");
        checkOperations.addNewCheckoutItem(transID, "B");
        checkOperations.addNewCheckoutItem(transID, "A");
        checkOperations.addNewCheckoutItem(transID, "A");
        checkOperations.addNewCheckoutItem(transID, "C");
        checkOperations.addNewCheckoutItem(transID, "A");
        
        // generate the till roll
        System.out.println(checkOperations.generateTillRoll(transID).toString());
    }

    /**
     * Test of removeCheckoutItem method, of class CheckoutOperations.
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveCheckoutItem() throws Exception {
        System.out.println("removeCheckoutItem 'Q' from the stack");
        
        // add Q as an available stock item
        StockCatalogue.putStockEntry(new StockUnitItem("Q", 999));
        
        // create a new session id and get the id
        int transID = checkOperations.createNewCheckoutSession();
        System.out.println(String.format("removeCheckoutItem transaction id is %d", transID));
        
        // add q to the list
        checkOperations.addNewCheckoutItem(transID, "Q");
        
        // test that it has 'Q' in the list
        assertTrue(SalesOrderLedger.returnCustomerSession(transID).itemExists("Q"));
        
        // remove the item from the stack
        checkOperations.removeCheckoutItem(transID, "Q");
        
        // test that 'Q' is NOT in the list
        assertFalse(SalesOrderLedger.returnCustomerSession(transID).itemExists("Q"));
    }

    /**
     * Test of removeStockItem method, of class CheckoutOperations.
     * @throws chulley.supermarket.exceptions.EXInvalidStockID
     */
    @Test
    public void testRemoveStockItem() throws EXInvalidStockID {
        System.out.println("removeStockItem :: Item H");
        
        // add stock item H to the main stack and test
        StockCatalogue.putStockEntry(new StockUnitItem("H", 56));
        assertTrue(StockCatalogue.returnStockCatalogue().containsKey("H"));
        
        // now remove it
        checkOperations.removeStockItem("H");
        assertFalse(StockCatalogue.returnStockCatalogue().containsKey("H"));
    }

    /**
     * Test of removeSpecialOffer method, of class CheckoutOperations.
     */
    @Test
    public void testRemoveSpecialOffer() throws EXInvalidStockID {
        System.out.println("removeSpecial :: Item B");
        
        // now remove it
        checkOperations.removeSpecialOffer("B");
        assertFalse(SpecialOffers.returnSpecialOffers().containsKey("B"));
    }

    /**
     * Test of returnCatalogueContents method, of class CheckoutOperations.
     */
    @Test
    public void testReturnCatalogueContents() {
        System.out.println(String.format("returnCatalogueContents :: %s", checkOperations.returnCatalogueContents(0).toString()));
    }

    /**
     * Test of returnSpecialOffers method, of class CheckoutOperations.
     */
    @Test
    public void testReturnSpecialOffers() {
        System.out.println(String.format("testReturnSpecialOffers :: %s", checkOperations.returnSpecialOffers(0).toString()));
    }


    /**
     * Test of addNewStockItem method, of class CheckoutOperations.
     * @throws chulley.supermarket.exceptions.EXInvalidItemValue
     */
    @Test
    public void testAddNewStockItem() throws EXInvalidItemValue {
        System.out.println("testAddNewStockItem :: Adding P");
        
        // add P to the stock item stack
        checkOperations.addNewStockItem("P", Integer.toString(65));
        
        // check it!
        assertTrue(StockCatalogue.returnStockCatalogue().containsKey("P"));
    }

    /**
     * Test of addSpecialOffer method, of class CheckoutOperations.
     * @throws chulley.supermarket.exceptions.EXInvalidItemValue
     */
    @Test
    public void testAddSpecialOffer() throws EXInvalidItemValue {
        System.out.println("testAddSpecialOffer :: Adding P");
        
        // add P to the special items
        checkOperations.addSpecialOffer("P", Integer.toString(5), Integer.toString(65));
        
        // check it!
        assertTrue(SpecialOffers.returnSpecialOffers().containsKey("P"));
    }
    
}
