$(document).ready(function () {

    $('.pulsantieraModificaEnteConvenz').hide();

    $('.confermaModificaEnteConvenz').dialog({
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
                window.location = parte[0] + "?operation=confermaModificaEnteConvenz";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaModificaEnteConvenz";
            }
        }
    });
});