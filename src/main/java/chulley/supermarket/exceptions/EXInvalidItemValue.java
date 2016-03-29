/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chulley.supermarket.exceptions;

/**
 * Internal Stock Value is incorrect
 * @author Chris
 */
public class EXInvalidItemValue extends Exception {
    
    /**
     * special constructor to prepare the message
     * @param itemType
     * @param itemMeasure
     * @param itemID
     */
    public EXInvalidItemValue(String itemType, String itemMeasure,  String itemID) {
        super(String.format("A %s Item %s of %s is not valid", itemType, itemMeasure, itemID));
    }
}
