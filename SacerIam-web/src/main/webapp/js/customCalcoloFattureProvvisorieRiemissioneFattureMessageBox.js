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

    ////////////////////////////////////
    // MessageBox FATTURE PROVVISORIE //
    ////////////////////////////////////
    $('.pulsantieraCalcoloFattureProvvisorie').hide();   
    $('.customCalcoloFattureProvvisorie').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",       
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "AmministrazioneEntiConvenzionati.html?operation=confermaCreazioneFattureProvvisorie&annoTestata="+ $('#Anno_testata_provv').val()+"&annoFattServizi=" + $('#Anno_fatt_servizi_provv').val();
            },
            "No": function () {
                $(this).dialog("close");
            }
        }
    });
    
    /////////////////////////////////////////////
    // MessageBox RIEMISSIONE FATTURE STORNATE //
    ////////////////////////////////////////////
    $('.pulsantieraRiemissioneFattureStornate').hide();   
    $('.customRiemissioneFattureStornate').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",       
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "AmministrazioneEntiConvenzionati.html?operation=confermaRiemissioneFattureStornate&annoTestata="+ $('#Anno_testata_storn').val();
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
