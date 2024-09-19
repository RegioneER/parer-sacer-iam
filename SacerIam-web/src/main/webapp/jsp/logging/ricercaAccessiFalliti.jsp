<%@ page import="it.eng.saceriam.slite.gen.form.GestioneAccessiFallitiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%= GestioneAccessiFallitiForm.AccessiFallitiList.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%= GestioneAccessiFallitiForm.AccessiFallitiList.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.DT_EVENTO_DA%>" colSpan="1" width="w0" />
                <slf:doubleLblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.ORA_RIF_DA%>" name2="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.MIN_RIF_DA%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.DT_EVENTO_A%>" colSpan="1" width="w0" />
                <slf:doubleLblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.ORA_RIF_A%>" name2="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.MIN_RIF_A%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.NM_USERID%>" colSpan="2" width="w20" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.TIPO_EVENTO%>" colSpan="2" width="w20" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.INCL_UTENTI_AUTOMI%>" colSpan="2" width="w20" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.INCL_LOGIN__OK%>" colSpan="2" width="w20" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.NM_NOME_USER%>" colSpan="2" width="w20" />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.NM_COGNOME_USER%>" colSpan="2" width="w20" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.DS_EMAIL_USER%>" colSpan="2" width="w20" />
                <sl:newLine />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.CD_FISC_USER%>" colSpan="2" width="w20" />
                <slf:lblField name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.CD_ID_ESTERNO%>" colSpan="2" width="w20" />
            </slf:fieldSet>

            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField  name="<%=GestioneAccessiFallitiForm.FiltriAccessiFalliti.RICERCA_ACCESSI_FALLITI%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <!--  piazzo la lista con i risultati -->
            <slf:list name="<%= GestioneAccessiFallitiForm.AccessiFallitiList.NAME%>" />
            <slf:listNavBar  name="<%= GestioneAccessiFallitiForm.AccessiFallitiList.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
