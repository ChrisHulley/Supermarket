/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.engine.checkout;

// class imports
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * test class for the sales ledger class functionality
 * @author hulleyc
 */
public class SalesOrderLedgerTest {
    
    /**
     * Test of generateNewTransactionId method, of class SalesOrderLedger.
     */
    @Test
    public void testGenerateNewTransactionId() {
        System.out.println("generateNewTransactionId");
        
        // get a new transaction id
        int currentVal = SalesOrderLedger.generateNewTransactionId();
        int newVal = SalesOrderLedger.generateNewTransactionId();
        System.out.println(String.format("old %d, new %d", currentVal, newVal));
        
        // test they have incremented by one
        assertEquals((currentVal + 1), newVal);
    }

    /**
     * Test of updateCustomerSession method, of class SalesOrderLedger.
     */
    @Test
    public void testUpdateCustomerSession() {
        System.out.println("updateCustomerSession");
        
        // get the new transaction id
        int transactionID = SalesOrderLedger.generateNewTransactionId();
        
        // test a session exists in the stack
        assertEquals(transactionID, SalesOrderLedger.returnCustomerSession(transactionID).getSessionID());
        System.out.println(SalesOrderLedger.returnCustomerSession(transactionID).toString());
    }
    
}
