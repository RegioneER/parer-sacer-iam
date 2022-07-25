<%@ page import="it.eng.saceriam.slite.gen.form.GestioneNewsForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Ricerca News" />

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />

        <sl:content>
            
           <slf:messageBox />
            <sl:newLine skipLine="true"/>
            
            <slf:fieldSet borderHidden="false">
                <slf:lblField name="<%=GestioneNewsForm.FiltriNews.DS_OGGETTO_FILTER%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
                <slf:lblField name="<%=GestioneNewsForm.FiltriNews.DT_INI_PUBBLIC_FILTER%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
                <slf:lblField name="<%=GestioneNewsForm.FiltriNews.DT_FIN_PUBBLIC_FILTER%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
            </slf:fieldSet>
            
            <sl:newLine skipLine="true"/>

            <sl:pulsantiera>
                <slf:lblField  name="<%=GestioneNewsForm.FiltriNews.RICERCA_NEWS%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <slf:list   name="<%=GestioneNewsForm.ListaNews.NAME%>" />
            <slf:listNavBar  name="<%=GestioneNewsForm.ListaNews.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
