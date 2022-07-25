<%@ page import="it.eng.saceriam.slite.gen.form.GestioneHelpOnLineForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Dettaglio help online" >
        <script type='text/javascript'>
            
            $(document).ready(function () {

                gestHelpOnLine_onLoad( $('#Ti_help_on_line') );
                
                /*
                 * Applicazione eventi di cambio valore sui ComboBox
                 */
                $('#Ti_help_on_line').change(function () {
                    gestHelpOnLine_onLoad( $(this) );
                });
                /*
                $('#Nm_applic').change(function () {
                    $('#Nm_entry_menu').val(""); 
                    $('#Nm_pagina').val(""); 
                    $('#Nm_pagina').attr("disabled", true); 
                    $('#Nm_entry_menu').attr("disabled", true); 
                });
                */
                var valoreHelp=$('#Preview_hidden_field').val();
                if (valoreHelp!='') {
                    showHelp(valoreHelp);
                }
        
            });
            
            function gestHelpOnLine_onLoad(componenteTipoHelp) {
                var valore=componenteTipoHelp.val();
                if (valore=='HELP_PAGINA') {
                    $('#Nm_pagina').attr("disabled", false); 
                    $('#Nm_entry_menu').val(""); 
                    $('#Nm_entry_menu').attr("disabled", true); 
                } else if (valore=='HELP_RICERCA_DIPS'){
//                    $('#Nm_pagina').val(""); 
                    $('#Nm_pagina').attr("disabled", false); 
                    $('#Nm_entry_menu').attr("disabled", false); 
                } else {
                    $('#Nm_pagina').val(""); 
                    $('#Nm_entry_menu').val(""); 
                    $('#Nm_pagina').attr("disabled", true); 
                    $('#Nm_entry_menu').attr("disabled", true); 
                }
            }
            
            function showHelp(valoreHelp) {
                var wWidth = $(window).width();
                var wHeight = $(window).height();

                $('#helpDialog').dialog({
                    autoOpen: true,
                    width: wWidth * 0.9,
                    height: wHeight * 0.9,
                    modal: true,
                    resizable: true,
                    title: 'Help On Line',
                    closeOnEscape: true,
                    close: function () {
                        $(this).html('');
                        $('#Preview_hidden_field').val('');
                    },
                    position: {my: "center", at: "center"},
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                        }
                    }
                });
                
                $.base64.utf8decode = true;				
//                $.base64.utf8encode = true;				
                var decodedString = $.base64.atob(valoreHelp,true);
                if (valoreHelp.length>0) {
                    $('#helpDialog').html(decodedString).first().focus();
                    $('#helpDialog').dialog( "open" );
                    $('#Preview_hidden_field').val("");
                }
            }
            
    </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="${sessionScope['###_FORM_CONTAINER']['dettaglioHelpOnLine'].status ne 'view'}">
            <slf:messageBox />
            <slf:listNavBarDetail name="<%=GestioneHelpOnLineForm.ListaHelpOnLine.NAME%>" />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="Dettaglio help"/>
            <slf:fieldSet legend="Dettaglio help" >
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.NM_APPLIC%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.TI_HELP_ON_LINE%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.NM_PAGINA%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.NM_ENTRY_MENU%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.DT_INI_VAL%>" colSpan="4" controlWidth="w20"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.DT_FINE_VAL%>" colSpan="4" controlWidth="w20"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.DS_FILE_HELP_ONLINE%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.DS_FILE_ZIP_HELP_ONLINE%>" colSpan="4" controlWidth="w40" />
                <sl:newLine />

                <div>
                    <input type="hidden" id="Preview_hidden_field" name="Preview_hidden_field" value="${Preview_hidden_field}"/>
                </div>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.PREVIEW%>" colSpan="2" />
                <slf:lblField name="<%=GestioneHelpOnLineForm.DettaglioHelpOnLine.DOWNLOAD_FILE%>" colSpan="2" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
