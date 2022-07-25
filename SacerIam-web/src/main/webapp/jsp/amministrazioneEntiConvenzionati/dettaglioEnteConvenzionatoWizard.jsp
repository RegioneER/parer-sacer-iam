<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.InserimentoEnteConvenzionatoWizard.DESCRIPTION%>" >
        <script>
            $(document).ready(function () {
                // Se la JSP viene ricaricata quando il flag è "checked" devo far scattare il trigger
                // per applicare lo stato "readonly" e la visualizzazione in sola lettura ai soli campi gestiti 
                if ($("#Fl_acc_non_approv").is(":checked")) {
                    var parameters = $('#spagoLiteAppForm').serializeArray();
                    $.post("AmministrazioneEntiConvenzionati.html?operation=triggerAccordoWizardDetailFl_acc_non_approvOnTriggerJs", parameters).done(function (jsonData) {
                        CAjaxInputFormWalk(jsonData);
                        $('#loading').remove();
                    });
                }

                // Registro l'evento che effettua una chiamata Ajax al trigger che setta lo stato in sola lettura o editabile 
                // ai campi gestiti in base al flag alzato o abbassato
                $("#Fl_acc_non_approv").change(function () {
                    var parameters = $('#spagoLiteAppForm').serializeArray();
                    $.post("AmministrazioneEntiConvenzionati.html?operation=triggerAccordoWizardDetailFl_acc_non_approvOnTriggerJs", parameters).done(function (jsonData) {
                        CAjaxInputFormWalk(jsonData);
                        $('#loading').remove();
                    });
                });      
                
                // "Trucco" per generare, attraverso la chiamata ad un bottone, la generazione
                // della lista contenente i tipi servizio
                $("#Id_tariffario").change(function () {
                    $("[name='operation__generaTabellaTipiServizioPerWz']").trigger("click");
                });
                $("[name='operation__generaTabellaTipiServizioPerWz']").css("display", "none");  
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
//                                .append("<input type='hidden' name='" + name + "' id='" + name + "_hidden' class='readonly' value='" + value + "'/>")
                                .append("<div  id='" + name + "' class='" + className + " readonly' >" + value + "</div>");
//                        element.attr('disabled', true);
                        element.hide();
                    } else if (state === 'edit') {
                        if (element.hasClass('required')) {
                            label.text('* ' + label.text().replace(/: /g, ''));
                        } else {
                            label.text(label.text().replace(/: /g, ''));
                        }
//                        element.attr('disabled', false);
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

            <c:if test="${!empty requestScope.customModificaEnteConvenzMessageBox}">
                <div class="messages confermaModificaEnteConvenz ">
                    <ul>
                        <li class="message warning ">Sono stati inseriti i dati di approvazione di un nuovo accordo, si vuole procedere?</li>
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
            <script type="text/javascript" src="<c:url value='/js/customModificaEnteConvenzMessageBox.js'/>" ></script>
            <script type="text/javascript" src="<c:url value='/js/customControlloDateInsEnteMessageBox.js'/>" ></script>
            <slf:wizard name="<%= AmministrazioneEntiConvenzionatiForm.InserimentoEnteConvenzionatoWizard.NAME%>">
                <slf:wizardNavBar name="<%=AmministrazioneEntiConvenzionatiForm.InserimentoEnteConvenzionatoWizard.NAME%>" />
                <slf:step name="<%= AmministrazioneEntiConvenzionatiForm.InserimentoEnteConvenzionatoWizard.STEP1%>" >
                    <slf:fieldSet borderHidden="true">
                        <c:choose>
                            <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'insert') }">
                                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.NM_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            </c:when>
                            <c:otherwise>
                                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.ID_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            </c:otherwise>
                        </c:choose>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DT_INI_VAL_APPART_AMBIENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DT_FIN_VAL_APPART_AMBIENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.ID_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.NM_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.TI_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DT_CESSAZIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <!--MEV#19333-->
                        <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.ID_AMBIENTE_ENTE_CONVENZ_NUOVO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />--%>
                        <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.ID_ENTE_CONVENZ_NUOVO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />--%>
                        <!--end MEV#19333-->
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.SedeLegaleSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DS_VIA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_CAP_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DS_CITTA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.ID_PROV_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_NAZIONE_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.CodiciSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.TI_CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_FISC%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DS_CATEG_ENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DT_RICH_MODULO_INFO%>" width="w100" controlWidth="w20" labelWidth="w20"/><sl:newLine />                            
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AmbitoTerritorialeSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_AMBITO_TERRIT_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_AMBITO_TERRIT_PROVINCIA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                         <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DatiFatturazioneSection.NAME%>" styleClass="importantContainer w100">                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.CD_UFE%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DS_UFE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.ID_CD_IVA%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.DS_IVA%>" colSpan="2"/><sl:newLine />                                       
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.TI_MOD_PAGAM%>" colSpan="2"/><sl:newLine />                                       
                </slf:section>
                <sl:newLine skipLine="true"/>
                        <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.CALCOLO_FATTURE_PROVVISORIE%>" width="w25"/>--%>
                    </slf:fieldSet>      

                    <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneSection.NAME%>" styleClass="noborder w100">
                        <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneList.NAME%>" multiRowEdit="true"/>
                    </slf:section>
                    <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriConservazioneSection.NAME%>" styleClass="noborder w100">
                        <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriConservazioneList.NAME%>" multiRowEdit="true"/>
                    </slf:section>
                    <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriGestioneSection.NAME%>" styleClass="noborder w100">
                        <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriGestioneList.NAME%>" multiRowEdit="true"/>
                    </slf:section>  
                </slf:step>

                <slf:step name="<%= AmministrazioneEntiConvenzionatiForm.InserimentoEnteConvenzionatoWizard.STEP2%>" >
                    <slf:fieldSet borderHidden="true">
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NM_ENTE_SIAM%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_NOTE_ENTE_ACCORDO%>" colSpan="2"/><sl:newLine />                              
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_ENTE_CONVENZ_GESTORE%>" width="w100" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NM_ENTE_CONVENZ_GESTORE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_ENTE_CONVENZ_CONSERV%>" width="w100" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NM_ENTE_CONVENZ_CONSERV%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_ENTE_CONVENZ_AMMINISTRATORE%>" width="w100" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NM_ENTE_CONVENZ_AMMINISTRATORE%>" colSpan="2"/><sl:newLine />
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.IdAccordoSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_ACCORDO_ENTE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NM_ENTE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NM_STRUT%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_REGISTRO_REPERTORIO%>" colSpan="2"/>                    
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ACCORDO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.AA_REPERTORIO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_KEY_REPERTORIO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_REG_ACCORDO%>" colSpan="2"/><sl:newLine />  
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_FIRMATARIO_ENTE%>" colSpan="2"/><sl:newLine />                                                          
                        </slf:section>
                       
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoAccordoSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.TI_SCOPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.FL_ACC_NON_APPROV%>" colSpan="2"/><sl:newLine />                            
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_ATTO_ACCORDO%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_ATTO_ACCORDO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_DEC_ACCORDO_INFO%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_SCAD_ACCORDO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_DEC_ACCORDO%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_FINE_VALID_ACCORDO%>" colSpan="2"/><sl:newLine />                            
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.FL_RECESSO%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_NOTA_RECESSO%>" colSpan="2"/><sl:newLine />                            
                            <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_CLASSE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_ABITANTI%>" colSpan="2"/><sl:newLine />--%>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_NOTE_ACCORDO%>" colSpan="4"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_NOTA_FATTURAZIONE%>" colSpan="2"/><sl:newLine />
                        </slf:section>
                         <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoAdozioneAccordoSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_REGISTRO_DETERMINA%>" colSpan="2"/>                    
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ACCORDO_ADOZIONE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.AA_DETERMINA%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_KEY_DETERMINA%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DT_DETERMINA%>" colSpan="2"/><sl:newLine />  
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoRimborsoCostiSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_TIPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.FL_PAGAMENTO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_TARIFFARIO%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_CLUSTER%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_FASCIA_STANDARD%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_FASCIA_MANUALE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_TIPO_UD_STANDARD%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_TIPO_UD_MANUALE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.IM_ATTIV_DOC_AMM%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_REFERTI_STANDARD%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_REFERTI_MANUALE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.IM_ATTIV_DOC_SANI%>" colSpan="2"/><sl:newLine />
                           <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_STUDIO_DICOM%>" colSpan="2"/><sl:newLine />
                           <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NI_STUDIO_DICOM_PREV%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_NOTA_ATTIVAZIONE%>" colSpan="2"/><sl:newLine />                                                     
                        </slf:section>      
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.InfoRimborsoCostiTipoServizioSection.NAME%>" styleClass="importantContainer w100">
                            <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList.NAME%>" multiRowEdit="true" />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.GENERA_TABELLA_TIPI_SERVIZIO_PER_WZ%>" colSpan="2"/>
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <%--<slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DatiFatturazioneSection.NAME%>" styleClass="importantContainer w100">
                            
                            <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_UFE%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_UFE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_CD_IVA%>" colSpan="2"/>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.DS_IVA%>" colSpan="2"/><sl:newLine />--%>
                            <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_COGE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_CAPITOLO%>" colSpan="2"/><sl:newLine />              
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_CIG%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_CUP%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_RIF_CONTAB%>" colSpan="2"/><sl:newLine />  
                            <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_DDT%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_ODA%>" colSpan="2"/><sl:newLine />--%>                            
                        <%--</slf:section>--%>
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.CampiDeprecatiSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ID_CLASSE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.NI_ABITANTI%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.ORDINE%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_CIG%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_CUP%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_RIF_CONTAB%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.CD_CLIENTE_FATTURAZIONE%>" colSpan="2"/><sl:newLine />
                        </slf:section>
                        <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail.RICALCOLO_SERVIZI_EROGATI%>" colSpan="2"/><sl:newLine />--%>
                    </slf:fieldSet>
                    <c:if test="${(sessionScope['###_FORM_CONTAINER']['accordiList'].status eq 'view') }">
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.GestioniAccordoSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" />
                        </slf:section>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ModuliInformazioniSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" />
                        </slf:section>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ServiziErogatiSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" />
                        </slf:section>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" />
                        </slf:section>
                    </c:if> 
                </slf:step>
            </slf:wizard>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
