/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.exceptions;

/**
 * Internal Transaction ID does not exist error
 * @author Chris
 */
public class EXInvalidTransactionID extends Exception {
    
    /**
     * special constructor to prepare the message
     * @param transactionID
     */
    public EXInvalidTransactionID(int transactionID) {
        super(String.format("Transaction ID %d does not exist", transactionID));
    }
}
