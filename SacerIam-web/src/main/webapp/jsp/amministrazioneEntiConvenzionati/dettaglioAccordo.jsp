<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DESCRIPTION%>">
        <script>
            $(document).ready(function () {

                $('#Fascia_da_occupazione').css({"background-color": $('#Colore_fascia').text()});
        
                // Se la JSP viene ricaricata quando il flag è "checked" devo far scattare il trigger
                // per applicare lo stato "readonly" e la visualizzazione in sola lettura ai soli campi gestiti 
                if ($("#Fl_acc_non_approv").is(":checked")) {
                    var parameters = $('#spagoLiteAppForm').serializeArray();
                    $.post("AmministrazioneEntiConvenzionati.html?operation=triggerAccordoDetailFl_acc_non_approvOnTriggerJs", parameters).done(function (jsonData) {
                        CAjaxInputFormWalk(jsonData);
                        $('#loading').remove();
                    });
                }

                // Registro l'evento che effettua una chiamata Ajax al trigger che setta lo stato in sola lettura o editabile 
                // ai campi gestiti in base al flag alzato o abbassato
                $("#Fl_acc_non_approv").change(function () {
                    var parameters = $('#spagoLiteAppForm').serializeArray();
                    $.post("AmministrazioneEntiConvenzionati.html?operation=triggerAccordoDetailFl_acc_non_approvOnTriggerJs", parameters).done(function (jsonData) {
                        CAjaxInputFormWalk(jsonData);
                        $('#loading').remove();
                    });
                });
                
                 // "Trucco" per effettuare, attraverso la chiamata ad un bottone, la generazione
                // della lista contenente i tipi servizio
                $("#Id_tariffario").change(function () {
                    $("[name='operation__generaTabellaTipiServizio']").trigger("click");
                });
                $("[name='operation__generaTabellaTipiServizio']").css("display", "none");  
                
                  // "Trucco" per generare, attraverso la chiamata ad un bottone, 
                 // il controllo sulla visualizzazione o meno del tasto inserisci annualità
                $("#Fl_pagamento").change(function () {
                    $("[name='operation__abilitaInserisciAnnualita']").trigger("click");
                });
                $("[name='operation__abilitaInserisciAnnualita']").css("display", "none"); 
                
                 // "Trucco" per generare, attraverso la chiamata ad un bottone, 
                 // il controllo sulla visualizzazione o meno del tasto inserisci annualità
                $("#Id_tipo_accordo").change(function () {
                    $("[name='operation__abilitaInserisciAnnualita']").trigger("click");
                });
                
                 // "Trucco" per generare, attraverso la chiamata ad un bottone, 
                 // il controllo sulla visualizzazione o meno del tasto inserisci annualità
                $("#Dt_scad_accordo").change(function () {
                    $("[name='operation__abilitaInserisciAnnualita']").trigger("click");
                });

                // popup chiusura accordo
                $('.chiusuraAccordoBox').dialog({
                    autoOpen: true,
                    width: 600,
                    modal: true,
                    closeOnEscape: true,
                    resizable: false,
                    dialogClass: "alertBox",
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                            var note = $(".chiusuraAccordoBox #Note").val();
                            var data = $(".chiusuraAccordoBox #Dt_chiusura").val();
                            var navTable = $("input[name='mainNavTable']").val();
                            window.location = "AmministrazioneEntiConvenzionati.html?operation=eseguiChiusuraAccordo&Note=" + note + "&Data=" + data + "&mainNavTable=" + navTable;
                        },
                        "Annulla": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            });

            function CAjaxInputFormWalk(jsonData) {
                switch (jsonData.type) {
                    case "Form":
                    case "Fields":
                        $.each(jsonData.map, function (property, value) {
                            CAjaxInputFormWalk(value);
                        });
                        break;
                    case "Input":
                        // SECTION: ID Accordo
                        if (jsonData.name === 'Aa_repertorio') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        if (jsonData.name === 'Cd_key_repertorio') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        if (jsonData.name === 'Dt_reg_accordo') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }

                        // SECTION: Informazioni sull'accordo
                        if (jsonData.name === 'Ds_atto_accordo') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        if (jsonData.name === 'Dt_atto_accordo') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        if (jsonData.name === 'Dt_dec_accordo') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        if (jsonData.name === 'Dt_scad_accordo') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        if (jsonData.name === 'Dt_fine_valid_accordo') {
                            var obj = $('#' + jsonData.name);
                            obj.addClass('required');
                            obj.val(jsonData.value);
                            obj.text(jsonData.value);
                            readOnlyElement(obj, jsonData.state);
                            break;
                        }
                        break;
                    case "ComboBox":
                        // SECTION: ID Accordo
                        if (jsonData.name === 'Cd_registro_repertorio') {
                            var obj = $('#' + jsonData.name);
                            obj.val(jsonData.value);
                            var prevCssWidth = obj.outerWidth(true);
                            readOnlyElement(obj, jsonData.state);
                            if (obj.is('select')) {
                                $.each(jsonData.map, function (index, value) {
                                    obj.children().remove();
                                    if (jsonData.withEmptyVal) {
                                        obj.append("<option value=\"\"></option>");
                                    }
                                    $.each(value, function (key, val) {
                                        var selected = "";
                                        key = key.replace(/_/, "");
                                        if (key == jsonData.value) {
                                            selected = " selected=\"selected\"";
                                        }
                                        obj.append("<option " + selected + " value=\"" + key + "\">" + val + "</option>");
                                        if (prevCssWidth) {
                                            obj.css({
                                                width: prevCssWidth
                                            });
                                        }
                                    });
                                });
                            } else {
                                if (jsonData.map && jsonData.map.length > 0) {
                                    obj.val(jsonData.map[0]['_' + jsonData.value]);
                                    obj.text(jsonData.map[0]['_' + jsonData.value]);
                                }
                                // Elimina la propagazione della post
                                return false;
                            }
                        }
                        break;
                }
            }

            function readOnlyElement(element, state) {
                var id = element.attr('id');
                var name = element.attr('name');
                var parents = element.parents('fieldset[id]');
                var value = element.val();
                var className = element.attr('class');
                var isInSection = parents.length > 0;
                var isSectionOpened = false;
                if (isInSection) {
                    var windowButton = $(parents[0]).find('button.windowButton');
                    var image = (windowButton.find('img:visible'));
                    isSectionOpened = (image.length > 0 ? image.attr("alt").indexOf("Chiudi") !== -1 : true);
                }
                if (!isInSection || isSectionOpened) {
                    // Se gli elementi sono all'interno di una sezione APERTA oppure non sono dentro una sezione
                    element.removeClass('displayNone');
                    var label = $(element.parent().parent().find('label[for=' + id + ']'));
                    label.removeClass('displayNone');

                    if (state === 'readonly') {
                        if (element.hasClass('required')) {
                            label.text(label.text().replace(/\* /g, '') + ": ");
                        } else {
                            label.text(label.text() + ": ");
                        }
                        element.parent()
                                .append("<div  id='" + name + "' class='" + className + " readonly' >" + value + "</div>");
                        element.hide();
                    } else if (state === 'edit') {
                        if (element.hasClass('required')) {
                            label.text('* ' + label.text().replace(/: /g, ''));
                        } else {
                            label.text(label.text().replace(/: /g, ''));
                        }
                        element.show();
                        element.parent().find('.readonly').remove();
                    }
                }
            }
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <c:if test="${!empty requestScope.customChiusuraAccordoMessageBox}">
                <div class="messages confermaChiusuraAccordo ">
                    <ul>
                        <li class="message warning ">Attenzione: esiste almeno un accordo con ${sessionScope['###_FORM_CONTAINER']['enteConvenzionatoDetail']['ti_ente_convenz'].value} l'ente di cui si richiede la chiusura dell'accordo. Si desidera procedere?</li>
                    </ul>
                </div>
            </c:if>
            <c:if test="${!empty requestScope.customChiusuraAccEnteProdutMessageBox}">
                <div class="messages confermaChiusuraAccordo ">
                    <ul>
                        <li class="message warning ">Attenzione: esiste almeno un'organizzazione riferita all'ente per il quale si richiede la chiusura dell'accordo. Si desidera procedere?</li>
                    </ul>
                </div>
            </c:if>
            <c:if test="${!empty requestScope.chiusuraAccordoBox}">
                <div class="messages chiusuraAccordoBox ">
                    <ul>
                        <li class="message info ">
                            <p>Inserisci le note e la data di chiusura dell'accordo:</p>
                            <sl:newLine />
                            <div class="containerLeft w100">
                                <label class="slLabel wlbl" for="Note">Note</label>
                                <TextArea id="Note" class="w70" name="Note" rows="4" cols="10" maxlength="4000"></TextArea>                                
                            </div>
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_CHIUSURA%>" colSpan="2"/>                           
                        </li>
                    </ul>
                </div>
            </c:if>
            <c:if test="${!empty requestScope.customControlloDateMessageBox}">
                <div class="messages salvaAccordoControlloDate ">
                    <ul>
                        <li class="message warning ">Attenzione: verrà inserito un accordo con data di inizio validità antecedente a quella di validità dell'ente. Si desidera procedere?</li>
                    </ul>
                </div>
            </c:if>
            <sl:newLine skipLine="true"/>
            <script type="text/javascript" src="<c:url value='/js/customChiusuraAccordoMessageBox.js'/>" ></script>
            <script type="text/javascript" src="<c:url value='/js/customControlloDateMessageBox.js'/>" ></script>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['accordiList'].table != null && sessionScope['###_FORM_CONTAINER']['accordiList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.AccordoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['accordiList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['accordiList'].table != null) && !(sessionScope['###_FORM_CONTAINER']['accordiList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.AccordiList.NAME%>" />  
            </c:if>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaAccordi'].table != null) && !(sessionScope['###_FORM_CONTAINER']['listaAccordi'].table['empty']) }">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.AccordoDetail.NAME%>" hideBackButton="false"/> 
            </c:if>
                        
            
            
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer w100">                    
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'insert') }">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ_CAMBIO%>" colSpan="2"/><sl:newLine />
                        </c:otherwise>
                    </c:choose>
                    <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />--%>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_NOTE_ENTE_ACCORDO%>" colSpan="2"/><sl:newLine />                              
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ENTE_CONVENZ_GESTORE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE_CONVENZ_GESTORE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ENTE_CONVENZ_CONSERV%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE_CONVENZ_CONSERV%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ENTE_CONVENZ_AMMINISTRATORE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE_CONVENZ_AMMINISTRATORE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.IdAccordoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ACCORDO_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_STRUT%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_REGISTRO_REPERTORIO%>" colSpan="2"/>                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.AA_REPERTORIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_KEY_REPERTORIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_REG_ACCORDO%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_FIRMATARIO_ENTE%>" colSpan="2"/><sl:newLine />                    
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoAccordoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.TI_SCOPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.FL_ACC_NON_APPROV%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_ATTO_ACCORDO%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_ATTO_ACCORDO%>" colSpan="2"/><sl:newLine />                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_DEC_ACCORDO_INFO%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_SCAD_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_DEC_ACCORDO%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_FINE_VALID_ACCORDO%>" colSpan="2"/><sl:newLine />                         
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.FL_RECESSO%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_NOTA_RECESSO%>" colSpan="2"/><sl:newLine />                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_NOTE_ACCORDO%>" colSpan="4"/><sl:newLine />    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_NOTA_FATTURAZIONE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                 <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoAdozioneAccordoSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_REGISTRO_DETERMINA%>" colSpan="2"/>                    
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ACCORDO_ADOZIONE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.AA_DETERMINA%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_KEY_DETERMINA%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_DETERMINA%>" colSpan="2"/><sl:newLine />  
                        </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoRimborsoCostiSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_TIPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.FL_PAGAMENTO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_TARIFFARIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_CLUSTER_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_FASCIA_STORAGE_STANDARD_ACCORDO%>" colSpan="4"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_FASCIA_STORAGE_MANUALE_ACCORDO%>" colSpan="4"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_TIPO_UD_STANDARD%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_TIPO_UD_MANUALE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.IM_ATTIV_DOC_AMM%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_REFERTI_STANDARD%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_REFERTI_MANUALE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.IM_ATTIV_DOC_SANI%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_STUDIO_DICOM%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_STUDIO_DICOM_PREV%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_NOTA_ATTIVAZIONE%>" colSpan="2"/><sl:newLine />                                                     
                </slf:section> 
                <sl:newLine skipLine="true"/>
                <c:if test="${(sessionScope['###_FORM_CONTAINER']['accordiList'].status eq 'view')}">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.MediaAnnualeOccupazioneStorageSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.STORAGE_OCCUPATO%>" width="w70" labelWidth="w30" controlWidth="w40"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DATO_CALCOLATO_DAL%>" width="w70" labelWidth="w30" controlWidth="w40" />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DATO_CALCOLATO_AL%>" width="w70" labelWidth="w30" controlWidth="w40" /><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.FASCIA_DA_ACCORDO%>" width="w70" labelWidth="w30" controlWidth="w40"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.FASCIA_DA_OCCUPAZIONE%>" width="w70" labelWidth="w30" controlWidth="w40"/><sl:newLine />                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.COLORE_FASCIA%>" width="w70" labelWidth="w30" controlWidth="w40"/><sl:newLine />                    
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.MediaAnnualeOccupazioneStorageNoDispSection.NAME%>" styleClass="importantContainer w100">
                    <slf:field name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.OCCUPAZIONE_NO_DISP%>" colSpan="4" controlWidth="w100"/><sl:newLine />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.MediaAnnualeOccupazioneStorageGestoreSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.STORAGE_OCCUPATO_GESTORE%>" width="w70" labelWidth="w30" controlWidth="w40"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DATO_CALCOLATO_DAL_GESTORE%>" width="w70" labelWidth="w30" controlWidth="w40" />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DATO_CALCOLATO_AL_GESTORE%>" width="w70" labelWidth="w30" controlWidth="w40" /><sl:newLine />                    
                </slf:section>
                <sl:newLine skipLine="true"/>    
                </c:if>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoRimborsoCostiTipoServizioSection.NAME%>" styleClass="importantContainer w100">
                    <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList.NAME%>" multiRowEdit="true"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.GENERA_TABELLA_TIPI_SERVIZIO%>" colSpan="2"/>
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoList.NAME%>" />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ABILITA_INSERISCI_ANNUALITA%>" colSpan="2"/>
                </slf:section>
                <sl:newLine skipLine="true"/>
               
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DatiDeprecatiSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_CLASSE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_ABITANTI%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_ODA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_CIG%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_CUP%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_RIF_CONTAB%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_CLIENTE_FATTURAZIONE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.RICALCOLO_SERVIZI_EROGATI%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CHIUSURA_ACCORDO%>" colSpan="2"/><sl:newLine />
            </slf:fieldSet>            
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['accordiList'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.GestioniAccordoSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" />
                </slf:section>
             
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ServiziErogatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" />
                </slf:section>
              
                  <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.FattureAccordoSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.FattureAccordoList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.FattureAccordoList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.FattureAccordoList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
