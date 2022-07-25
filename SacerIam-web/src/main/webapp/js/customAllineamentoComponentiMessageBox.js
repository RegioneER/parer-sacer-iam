function CustomBoxAllineamentoComponenti() {
    $('.pulsantieraAllineamentoComponenti').hide();
    $('.customBoxAllineamentoComponenti').dialog({
        autoOpen: true,
        width: 1000,
        modal: true,
        closeOnEscape: true,
        resizable: false,
        dialogClass: "alertBox",
        buttons: {
            "Continua": function () {
                $(this).dialog("close");
                window.location = "AllineaComponenti.html?operation=confermaAllineamentoComponenti";
            },
            "Annulla": function () {
                $(this).dialog("close");
                window.location = "AllineaComponenti.html?operation=annullaAllineamentoComponenti";
            }
        }
    });
}
