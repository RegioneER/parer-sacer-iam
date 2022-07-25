$(document).ready(function () {
    $('.salvaAccordoControlloDate').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Si": function () {
                $(this).dialog("close");
                window.location = "AmministrazioneEntiConvenzionati.html?operation=confermaSalvataggioControlloDateAccordo";
            },
            "No": function () {
                $(this).dialog("close");
                window.location = "AmministrazioneEntiConvenzionati.html?operation=annullaSalvataggioControlloDateAccordo";
            }
        }
    });
});