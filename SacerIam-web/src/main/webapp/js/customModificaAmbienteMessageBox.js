function CustomBoxModificaAmbiente() {
    $('.pulsantieraModificaAmbiente').hide();
    $('.customModificaAmbienteMessageBox').dialog({
        autoOpen: true,
        width: 600,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Continua": function () {
                $(this).dialog("close");
                window.location = "AmministrazioneEntiConvenzionati.html?operation=confermaSalvataggioModificaAmbiente";
            },
            "Annulla": function () {
                $(this).dialog("close");
                window.location = "AmministrazioneEntiConvenzionati.html?operation=annullaSalvataggioModificaAmbiente";
            }
        }
    });
}
