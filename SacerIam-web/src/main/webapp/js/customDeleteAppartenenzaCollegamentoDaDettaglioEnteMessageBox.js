$(document).ready(function () {

    $('.pulsantieraDeleteAppartenenzaCollegamentoDaDettaglioEnte').hide();

    $('.confermaDeleteAppartenenzaCollegamentoDaDettaglioEnte').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=confermaDeleteAppartenenzaCollegamentoDaDettaglioEnte";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaDeleteAppartenenzaCollegamentoDaDettaglioEnte";
            }
        }
    });
});