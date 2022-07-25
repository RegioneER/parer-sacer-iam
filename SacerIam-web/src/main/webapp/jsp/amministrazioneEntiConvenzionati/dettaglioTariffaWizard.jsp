<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Gestione tariffa">
        <script type="text/javascript">
            $(document).ready(function () {
                abilitaCampiTariffa();
            });

            function abilitaCampiTariffa() {
                $("#Tipo_tariffa").change(function () {
                    var value = $("#Tipo_tariffa").val();
                    if (value === 'VALORE_FISSO') {
                        $("#Im_valore_fisso_tariffa").attr("disabled", false);
                    } else {
                        $("#Im_valore_fisso_tariffa").val("");
                        $("#Im_valore_fisso_tariffa").attr("disabled", true);
                    }

                    if (value === 'VALORE_UNITARIO_SCAGLIONI_STORAGE') {
                        $("#Ni_quantita_unit").attr("disabled", false);
                    }
                    else {
                        $("#Ni_quantita_unit").val("");
                        $("#Ni_quantita_unit").attr("disabled", true);
                    }
                }).trigger('change');
            }
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <slf:wizard name="<%= AmministrazioneTariffariForm.InserimentoWizard.NAME%>">
                <slf:wizardNavBar name="<%=AmministrazioneTariffariForm.InserimentoWizard.NAME%>" />
                <sl:newLine skipLine="true"/>   

                <slf:step name="<%= AmministrazioneTariffariForm.InserimentoWizard.STEP1%>">
                    <slf:fieldSet borderHidden="false">
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_CLASSE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NM_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.DS_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.TIPO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.IM_VALORE_FISSO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NI_QUANTITA_UNIT%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                    </slf:fieldSet>
                </slf:step>

                <slf:step name="<%= AmministrazioneTariffariForm.InserimentoWizard.STEP2%>">
                    <slf:fieldSet borderHidden="false">
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_CLASSE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NM_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.DS_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.TIPO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.IM_VALORE_FISSO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NI_QUANTITA_UNIT%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <sl:newLine />
                    </slf:fieldSet>
                    <sl:newLine skipLine="true"/>
                    <slf:lblField name="<%= AmministrazioneTariffariForm.ScaglioniFields.NI_INI_SCAGLIONE%>" colSpan="2" controlWidth="w20"/>
                    <sl:newLine skipLine="true"/>
                    <slf:lblField name="<%= AmministrazioneTariffariForm.ScaglioniFields.NI_FINE_SCAGLIONE%>" colSpan="2" controlWidth="w20"/>
                    <sl:newLine skipLine="true"/>
                    <slf:lblField name="<%= AmministrazioneTariffariForm.ScaglioniFields.IM_SCAGLIONE%>" colSpan="2" controlWidth="w20"/>
                    <sl:newLine skipLine="true"/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%= AmministrazioneTariffariForm.ScaglioniFields.AGGIUNGI_SCAGLIONE%>" colSpan="2" controlWidth="w40" /> 
                    </sl:pulsantiera>
                    <sl:newLine skipLine="true"/>       
                    <slf:container width="w30">
                        <slf:section name="<%=AmministrazioneTariffariForm.ScaglioniSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneTariffariForm.ListaScaglioni.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneTariffariForm.ListaScaglioni.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneTariffariForm.ListaScaglioni.NAME%>" />
                        </slf:section>               
                    </slf:container>
                </slf:step>

                <slf:step name="<%= AmministrazioneTariffariForm.InserimentoWizard.STEP3%>">
                    <slf:section name="<%=AmministrazioneTariffariForm.RiepilogoTariffaSection.NAME%>" styleClass="noborder w100">
                        <slf:fieldSet borderHidden="false">
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_CLASSE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NM_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.DS_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.TIPO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.IM_VALORE_FISSO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                            <sl:newLine />
                            <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NI_QUANTITA_UNIT%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                            <sl:newLine />
                        </slf:fieldSet>
                    </slf:section>               
                </slf:step>

            </slf:wizard>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>