$(document).ready(function () {

    $('.pulsantieraDeleteVigilanza').hide();

    $('.confermaDeleteVigilanza').dialog({
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
                window.location = parte[0] + "?operation=confermaDeleteVigilanza";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaDeleteVigilanza";
            }
        }
    });
});