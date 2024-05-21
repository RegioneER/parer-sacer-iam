$(document).ready(function () {

    $('.pulsantieraDeleteAppartenenzaCollegamento').hide();

    $('.confermaDeleteAppartenenzaCollegamento').dialog({
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
                window.location = parte[0] + "?operation=confermaDeleteAppartenenzaCollegamento";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaDeleteAppartenenzaCollegamento";
            }
        }
    });
});