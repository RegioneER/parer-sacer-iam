function CMessagesAlertBox() {

    $('.confRemove').dialog({
        autoOpen : true,
        width : 600,
        modal : true,
        closeOnEscape : true,
        resizable: false,
        dialogClass: "alertBox",
        buttons : {
            "Sì" : function() {
                $(this).dialog("close");
                window.location = "GestioneAmbitoTerritoriale.html?operation=deleteNode";
            },
            "No" : function() {
                $(this).dialog("close");
                window.location = "GestioneAmbitoTerritoriale.html?operation=annulla";
            }
        }
    });
    
}