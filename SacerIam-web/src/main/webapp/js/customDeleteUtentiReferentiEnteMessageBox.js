 $(document).ready(function () {
    $('.pulsantieraDeleteReferentiEnteConvenz').hide();

    $('.confermaDeleteReferentiEnteConvenz').dialog({
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
                window.location = parte[0] + "?operation=confermaCancReferenteEnte";
            },
            "No": function () {
                $(this).dialog("close");
                var parte = window.location.pathname.split('/');
                window.location = parte[0] + "?operation=annullaCancReferenteEnte";
            }
        }
    });
});