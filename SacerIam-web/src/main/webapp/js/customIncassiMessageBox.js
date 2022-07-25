/* 
 * Override di CMessagesAlertBox() in classes.js
 */
function CMessagesAlertBox() {
    ////////////////////////
    // MessageBox di INFO //
    ////////////////////////
    $('.infoBox').dialog({
        autoOpen: true,
        width: 600,
        modal: false,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    }); 

    ////////////////////////////////
    // MessageBox IMPORTI INCASSO //
    ////////////////////////////////
    $('.pulsantieraImportiIncasso').hide();
    $('.customBoxImportiIncasso').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "GestioneFatturazioneServizi.html?operation=confermaSalvataggioIncasso";
            },
            "No": function () {
                $(this).dialog("close");
            }
        }
    });

    ///////////////////////
    // WARNING ED ERRORE //
    ///////////////////////
     $('.warnBox').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    });
    
    $('.errorBox').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    });

}
