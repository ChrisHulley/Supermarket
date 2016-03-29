/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// perform all jQuery in order
$.ajaxSetup({
    async: false
});

/*
 * resolves the full URL based on the current page
 * @returns the new URL
 */
function getFullURL() {

    // local variable for url
    var sURL = document.location.href;

    // return url stripped of the index
    return sURL.replace( "index.html", "" );
}

/*
 * Simple encapsulated method to render a button
 * @param {type} buttonId
 * @param {type} buttonName
 * @param {type} buttonText
 * @returns {String}
 */
function getButtonHTML(buttonId, buttonName, buttonText) {
    return '<input type="button" id="' + buttonId + '" name="' + buttonName + '" value="' + buttonText + '"/>';
};

/*
 * encapsulate the activities to set-up and refresh the screen items after and action
 * @returns nothing
 */
function updateScreenElements(transactionID) {
    
    // if transactionID is blank then default to zero (invalid transID) as blank will not correctly render the post command to REST
    if (transactionID === "") {
        transactionID = 0;
    }
    
    // re-populate the till roll with the new transaction id
    document.getElementById("PriceLists").innerHTML = buildCatalogueInfo(transactionID) + buildSpecialOffers(transactionID);
    document.getElementById("TillRoll").innerHTML = generateTillRoll();
    document.getElementById("NewItemID").focus();
    $('#NewItemID').val('');
}

/*
 * test whether the transaction exists in the sales ledger
 * @param {type} transactionID
 * @returns {String}
 */
function transactionIdExist(transactionID) {
    
    // init the session to false
    var sessionOk = false;
    
    // does the transaction id exist?
    $.post(getFullURL() + "/IsSessionAlive", {TransID : transactionID}, function( data ) {
        
        // simply set the value of the return
        sessionOk = data;
    });
    
    // return status
    return sessionOk;
}

/*
 * Build the CATALOGUE information from the REST Interface for the transaction
 * 
 * If there is no active session then allow the user to add and remove items from 
 * the list.  When there is an active session then the user cannot change the items
 * from the stock list
 * @returns {String}
 */
function buildCatalogueInfo(transactionID) {
        
    // start a new table
    var catalogueInfo = '<table id="stockTbl">';
    catalogueInfo += '<tr><th>Stock Items</th></tr>';
    
    // boolean for 'active' or 'new' transaction
    var sessionExists = transactionIdExist(transactionID);
    var stockItems = 0;
        
    // get the catalogue items and build
    $.post(getFullURL() + "/Catalogue", {TransID : transactionID }, function(data){

        
        // for each item in the catalogue
        for (var nIndex = 0, len = data.length; nIndex < len; nIndex++) {
            catalogueInfo += '<tr><td><input type="text" value="Item ' + data[nIndex].itemID + ', Unit Price ' + data[nIndex].unitPrice + '" readonly/></td>';
            
            // add the remove button for new transactions
            if (!sessionExists) {
                catalogueInfo += '<td>' + getButtonHTML('RemoveStock', data[nIndex].itemID, 'REMOVE') + '</td>';
            }
            
            // complete the line
            catalogueInfo += '</tr>';
        }
        
        // create new stock?
        if (!sessionExists) {
            
            // create new stock item and price
            catalogueInfo += '<tr><td><p>Click \'remove\' to delete a stock item from the main catalogue.  Click \'add/update\' to create or replace an existing stock item<br><br>';
            catalogueInfo += 'This functionality is only available when no active checkout session is loaded.  Once a checkout session is started the stock items are fixed for the transaction</p></td></tr>';
            catalogueInfo += '<tr><td><hr></td></tr><tr><td><p>New Stock ID & Value</p></td></tr>';
            catalogueInfo += '<tr><td><input style="text-transform:uppercase" type="text" alt="New Stock ID" id="NewStockID" value=""/>';
            catalogueInfo += '<input type="text" alt="New Stock Value" id="NewStockValue" value=""/></td>';
            catalogueInfo += '<td>' + getButtonHTML('AddStockItem', 'ADD', 'Add/Update') + '</td></tr>';
        } else {
            
            // no editing can be done as a transaction is in progress
            catalogueInfo += '<tr><td><p>No edits are allowed</p></td></tr>';
    }
        
        // set the stock items
        stockItems = data.length;
  
    });
    
    // finish the table
    catalogueInfo += '</table>';

    // add the summary information
    catalogueInfo += '<p id="right_txt">' + stockItems + ' stock items available</p><br>'; 
    
    // return the html
    return catalogueInfo;
}

/*
 * Build the SPECIAL OFFER information from the REST Interface
 * 
 * If there is no active session then allow the user to add and remove items from 
 * the list.  When there is an active session then the user cannot change the items
 * from the special offers list
 * @returns {String}
 */
function buildSpecialOffers(transactionID) {
        
    // start a new table
    var specialOffer = '<table id="specialTbl">';
    specialOffer += '<tr><th>Special Offers</th></tr>';
    
    // boolean for 'active' or 'new' transaction
    var sessionExists = transactionIdExist(transactionID);
    var specialOffers = 0;
        
    // get the special multi-buy items and build
    $.post(getFullURL() + "/SpecialOffers", {TransID : transactionID }, function(data){

        
        // for each item in the list
        for (var nIndex = 0, len = data.length; nIndex < len; nIndex++) {
            specialOffer += '<tr><td><input type="text" value="Buy ' + data[nIndex].multiBuyQty + ' of ' + data[nIndex].itemID + ' for ' + data[nIndex].multiBuyPrice + '" readonly/></td>';
            
            // add the remove button for new transactions
            if (!sessionExists) {
                specialOffer += '<td>' + getButtonHTML('RemoveSpecials', data[nIndex].itemID, 'REMOVE') + '</td>';
            }
            
            // complete the line
            specialOffer += '</tr>';
        }
        
        // create new special offer?
        if (!sessionExists) {
            
            // create new stock item and price
            specialOffer += '<tr><td><p>Click \'remove\' to delete a special offer.  Click \'add/update\' to create or replace an existing special offer<br><br>';
            specialOffer += 'This functionality is only available when no active checkout session is loaded.  Once a checkout session is started the special offers are fixed for the transaction</p></td></tr>';
            specialOffer += '<tr><td><hr></td></tr><tr><td><p>New Special Qty, ID & Value</p></td></tr>';
            specialOffer += '<tr><td><input type="text" alt="New Offer Quantity" id="NewOfferQty" value=""/>';
            specialOffer += '<input style="text-transform:uppercase" alt="New Offer ID" type="text" id="NewOfferID" value=""/>';
            specialOffer += '<input type="text" id="NewOfferValue" alt="New Offer Price" value=""/></td>';
            specialOffer += '<td>' + getButtonHTML('AddSpecialOffer', 'ADD', 'Add/Update') + '</td></tr>';
        } else {
            
            // no editing can be done as a transaction is in progress
            specialOffer += '<tr><td><p>No edits are allowed</p></td></tr>';
        }
        
        // set the special
        specialOffers = data.length;
  
    });   
    
    // finish the table
    specialOffer += '</table>';
        
    // complete the table html
    specialOffer += '<p id="right_txt">' + specialOffers + ' special offers available</p><br>'; 
    
    // return the html
    return specialOffer;
}

/*
 * Build the till roll from the current transaction id
 * @returns {String}
 */
function generateTillRoll() {
            
    // add the new checkout receipt
    var tillRoll = '<table id="checkout"><tr><th bgColor="gray">Checkout Till Roll</th></tr>';
    
    // get the till roll object information
    if (transactionIdExist($('#TransactionID').val())) {
        $.post(getFullURL() + "/TillRoll", {TransID : $('#TransactionID').val()}, function( data ) {

            // for each item in the catalogue
            for (var nIndex = 0, len = data.length; nIndex < len; nIndex++) {
                tillRoll += '<td><p id="TillRoll">' + data[nIndex] + '</p></td></tr>';
            }

        });
    } else {
        tillRoll += '<td><p id="TillRoll">no active checkout session</p></td></tr>';
    }
    
    // complete the table
    tillRoll += "</table>";
    
    // write to the body
    return tillRoll;
}

/*
 * ON CLICK to generate a new transaction id and begin a new session
 */
$(document).on('click','#NewTrans',function() {
        
    // get a new transaction id and reload body
    $.getJSON(getFullURL() + "/NewSession", function(data){
        $('#TransactionID').val(data);
    });
    
    // re-populate the till roll with the new transaction id
    document.getElementById("msgOutput").innerHTML = "";
    updateScreenElements($('#TransactionID').val());

});

/*
 * ON CLICK and searches for the specified transaction id
 */
$(document).on('click','#SearchTrans',function() {
        
    // does the transaction id exist?
    if (!transactionIdExist($('#TransactionID').val())) {
        
        // raise an error
        document.getElementById("msgOutput").innerHTML = $('#TransactionID').val() + " does not exist as a valid transaction";
        alert("\'" + $('#TransactionID').val() + "\' does not exist as a valid transaction");
    } else {
        document.getElementById("msgOutput").innerHTML = '';
    }
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());

});

/*
 * ON CLICK to rest the form and clear any transactions
 */
$(document).on('click','#ResetForm',function() {
        
    // clear the current transaction
    $('#TransactionID').val("");
    
    // re-populate the form
    updateScreenElements(0);

});

/*
 * ON CLICK to add a new stock item to the checkout basket
 */
$(document).on('click','#ADD',function(){
    
    // add a new item from the add list
    if (transactionIdExist($('#TransactionID').val())) {
        $.post(getFullURL() + "/AddNewCOItem", {TransID : $('#TransactionID').val(), StockID : $('#NewItemID').val().toUpperCase()})
            .done(document.getElementById("msgOutput").innerHTML = "")
            .fail(function (data) {
                document.getElementById("msgOutput").innerHTML = JSON.parse(data.responseText).message;
                alert(JSON.parse(data.responseText).message);
            });
    } else {
        var msgData = "Cannot add new Item until a transaction is \'active\'.";
        document.getElementById("msgOutput").innerHTML = msgData;
        alert(msgData);
    }
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());
});

/*
 * ON CLICK removal of an item from the checkout list
 */
$(document).on('click','#REMOVE',function(){
    
    // remove an item from the add list
    if (transactionIdExist($('#TransactionID').val())) {
        $.post(getFullURL() + "/RemoveCOItem", {TransID : $('#TransactionID').val(), StockID : $('#NewItemID').val().toUpperCase()})
            .done(document.getElementById("msgOutput").innerHTML = "")
            .fail(function (data) {
                document.getElementById("msgOutput").innerHTML = JSON.parse(data.responseText).message;
                alert(JSON.parse(data.responseText).message);
            });
    } else {
        var msgData = "Cannot remove an item from the basket until a transaction id \'active\'.";
        document.getElementById("msgOutput").innerHTML = msgData;
        alert(msgData);
    }
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());
});

/*
 * ON CLICK removal of stock item (only avaiable when a session is not active)
 */
$(document).on('click','#RemoveStock',function(){
    
    // remove the item from the stock list
    $.post(getFullURL() + "/RemoveStockItem", {StockID : this.name})
        .done(document.getElementById("msgOutput").innerHTML = "")
        .fail(function (data) {
            document.getElementById("msgOutput").innerHTML = JSON.parse(data.responseText).message;
            alert(JSON.parse(data.responseText).message);
        });
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());
});

/*
 * ON CLICK ADD a new stock item (only avaiable when a session is not active)
 */
$(document).on('click','#AddStockItem',function(){
    
    // remove the item from the stock list
    $.post(getFullURL() + "/AddNewStockItem", {StockID : $('#NewStockID').val().toUpperCase(), StockValue : $('#NewStockValue').val()})
        .done(document.getElementById("msgOutput").innerHTML = "")
        .fail(function (data) {
            document.getElementById("msgOutput").innerHTML = JSON.parse(data.responseText).message;
            alert(JSON.parse(data.responseText).message);
        });
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());
});

/*
 * ON CLICK removal of a special items (only avaiable when a session is not active)
 */
$(document).on('click','#RemoveSpecials',function(){
    
    // remove the item from the special items
    $.post(getFullURL() + "/RemoveSpecialOffer", {StockID : this.name})
        .done(document.getElementById("msgOutput").innerHTML = "")
        .fail(function (data) {
            document.getElementById("msgOutput").innerHTML = JSON.parse(data.responseText).message;
            alert(JSON.parse(data.responseText).message);
        });
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());
});

/*
 * ON CLICK ADD special items (only avaiable when a session is not active)
 */
$(document).on('click','#AddSpecialOffer',function(){
    
    // add a new item from the special offers
    $.post(getFullURL() + "/AddSpecialOffer", {SpecialID : $('#NewOfferID').val().toUpperCase(), SpecialQty : $('#NewOfferQty').val().toUpperCase(), SpecialValue : $('#NewOfferValue').val().toUpperCase()})
        .done(document.getElementById("msgOutput").innerHTML = "")
        .fail(function (data) {
            document.getElementById("msgOutput").innerHTML = JSON.parse(data.responseText).message;
            alert(JSON.parse(data.responseText).message);
        });
    
    // re-populate the till roll with the new transaction id
    updateScreenElements($('#TransactionID').val());
});

/*
 * The main function that is called when the document has loaded.  This will build the HTML
 * @param {type} param
 */
$(document).ready(function() {
    
    // begin to populate the form screen
    var formData = '<table id="masterTbl"><tr>';   
    
    // catpure the price list for the session or the default price list
    formData += '<td><div id="PriceLists">' + buildCatalogueInfo(0) + buildSpecialOffers(0) + '</div></td>';

    // add the line for the transaction id
    formData += '<td><table width="300px"><th>Transaction ID</th><tr>';
    formData += '<td><input type="text" id="TransactionID" value=""/></td>';
    formData += '</tr><tr><td>' + getButtonHTML('SearchTrans', 'SearchT', 'Search') + ' ' + getButtonHTML('NewTrans', 'New', 'New') + ' ' + getButtonHTML("ResetForm", "RESET", "Clear") + '</td></tr>';
    formData += '<tr><td><p>Press \'new\' to generate a new checkout session or key in a session number and click \'search\' to retrieve the basket and continue shopping</p></td></tr>';
    formData += '<tr><td><p>Press \'Clear\' to save the current transaction and enable editing of the stock and special offers for future transactions</p></td></tr></table>'; 

    // add/remove Items from the checkout
    formData += '<br><table width="300px"><th>Add/Remove from Basket</th><tr>';
    formData += '<td><input type="text" style="text-transform:uppercase" id="NewItemID" value=""/></td>';
    formData += '</tr><tr><td>' + getButtonHTML('ADD', 'Add', 'Add') + '  ' + getButtonHTML('REMOVE', 'Remove', 'Remove') + '</td></tr>';
    formData += '<tr><td><p>Key in a stock item letter and press \'add\' to place it in the basket or \'remove\' to take it back out of the basket</p></td></tr></table></td>';
    
    // get the till roll for the first time and wrap it up for further updates
    formData += '<td><div id="TillRoll"></div></td></tr></table>';
    
    // finally add a section for any error messages
    formData += '<h2 id="msgOutput"></h2>';
    
    // add to the body of the html page
    $(formData).appendTo($(document.body));
    
    // generate till roll now the transaction id is known 
    document.getElementById("TillRoll").innerHTML = generateTillRoll();
    
    // all done
});