$(document).ready(function () {

    $('.pulsantieraDeleteUtentiArk').hide();

    $('.confermaDeleteUtentiArk').dialog({
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
                window.location = parte[0] + "?operation=confermaCancUtenteArk";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaCancUtenteArk";
            }
        }
    });

    $('.pulsantieraDeleteReferentiDittaProduttrice').hide();

    $('.confermaDeleteReferentiDittaProduttrice').dialog({
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
                window.location = parte[0] + "?operation=confermaCancReferenteDittaProduttrice";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaCancReferenteDittaProduttrice";
            }
        }
    });

});