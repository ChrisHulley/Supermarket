/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.exceptions;

/**
 * Internal Stock ID does not exist error
 * @author Chris
 */
public class EXInvalidStockID extends Exception {
    
    /**
     * special constructor to prepare the message
     * @param itemID
     */
    public EXInvalidStockID(String itemID) {
        super(String.format("Stock Item ID %s does not exist", itemID));
    }
}
