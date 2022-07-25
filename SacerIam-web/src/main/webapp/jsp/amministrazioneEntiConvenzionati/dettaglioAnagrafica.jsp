<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['anagraficheList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['anagraficheList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['anagraficheList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.AnagraficheList.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.NM_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DT_FINE_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.SedeLegaleSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DS_VIA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_CAP_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DS_CITTA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.ID_PROV_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_NAZIONE_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.TI_CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_FISC%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_CLIENTE_SAP%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DS_CATEG_ENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AmbitoTerritorialeSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_AMBITO_TERRIT_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_AMBITO_TERRIT_PROVINCIA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.FL_ENTE_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DL_NOTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
            </slf:fieldSet>         
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
