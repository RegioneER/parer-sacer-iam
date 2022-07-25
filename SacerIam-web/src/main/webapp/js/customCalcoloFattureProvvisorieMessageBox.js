$(document).ready(function () {
  
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
});