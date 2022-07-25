<%@ page import="it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaServiziFatturati'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaServiziFatturati'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaServiziFatturati'].table['empty']) }">
                <slf:listNavBarDetail name="<%= GestioneFatturazioneServiziForm.ListaServiziFatturati.NAME%>" />  
            </c:if>      
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=GestioneFatturazioneServiziForm.ServizioErogatoSection.NAME%>" >                    
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NM_SERVIZIO_EROGATO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.FL_PAGAMENTO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.TIPO_FATTURAZIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NM_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.IM_VALORE_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.CD_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NM_SISTEMA_VERSANTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.DT_EROG_SERV_EROG%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <slf:section name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoSection.NAME%>" >                    
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NM_SERVIZIO_FATTURA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.AA_SERVIZIO_FATTURA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NI_UNITA_DOC_VERS%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NI_BYTE_VERS%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.NI_ABITANTI%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.MM_FATTURA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.CD_DDT%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.CD_ODA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.QUANTITA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.UNITA_MISURA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.IM_NETTO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.CD_IVA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.ServizioFatturatoDetail.IM_IVA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
            </slf:fieldSet>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
