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

    ////////////////////////
    // MessageBox FATTURA //
    ////////////////////////
    $('.pulsantieraSalvataggioFattura').hide();
    $('.customBoxSalvataggioFatturaControllo1').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "GestioneFatturazioneServizi.html?operation=thirdStepSaveFattura";
            },
            "No": function () {
                $(this).dialog("close");
            }
        }
    });
    $('.customBoxSalvataggioFatturaControllo2').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "GestioneFatturazioneServizi.html?operation=thirdStepSaveFattura";
            },
            "No": function () {
                $(this).dialog("close");
            }
        }
    });
    $('.customBoxSalvataggioFatturaControlloImporti').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                //window.location = "GestioneFatturazioneServizi.html?operation=confermaSalvataggioFattura&dataModifica="+ $('#Data_modifica').val()+"&noteModifica=" + $('#Note_modifica').val();
                window.location = "GestioneFatturazioneServizi.html?operation=fourthStepSaveFattura";
            },
            "No": function () {
                $(this).dialog("close");
            }
        }
    });
    $('.customBoxSalvataggioFatturaInserimentoDataNote').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        open: function(event, ui) {
            $('#Note_modifica').focus();
        },
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "GestioneFatturazioneServizi.html?operation=confermaSalvataggioFattura&dataModifica="+ $('#Data_modifica').val()+"&noteModifica=" + $('#Note_modifica').val();
            },
            "No": function () {
                $(this).dialog("close");
            }
        }
    });
    
     //////////////////////////////////////////////////////
    // MessageBox FATTURE STORNATE DA DETTAGLIO FATTURA //
    //////////////////////////////////////////////////////
    $('.pulsantieraRiemissioneFattureStornateDaFattura').hide();   
    $('.customRiemissioneFattureStornateDaFattura').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",       
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "GestioneFatturazioneServizi.html?operation=confermaRiemissioneFattureStornate&annoTestata="+ $('#Anno_testata_storn').val();
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
