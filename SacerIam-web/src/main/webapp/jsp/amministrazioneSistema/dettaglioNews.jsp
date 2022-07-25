<%@ page import="it.eng.saceriam.slite.gen.form.GestioneNewsForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Ricerca News" >
        <script type="text/javascript">
            $(document).ready( function() {
                $('table.list td  > a[href*="navigationEvent=delete"]').bind('click', function() {
                    $.ajax({
                        type: "POST",
                        url: "GestioneNews.html?operation=postData",
                        data: $("fieldset").serialize()
                    });
                })
            });
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false"/>
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>

            <slf:messageBox />    
            <sl:contentTitle title="Dettaglio News"/>

            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaNews'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= GestioneNewsForm.DettaglioNews.NAME%>" hideBackButton="true" /> 
            </c:if>   

            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaNews'].table['empty']) }">
                <slf:listNavBarDetail name="<%= GestioneNewsForm.ListaNews.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true"/>

            <slf:fieldSet >
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.ID_NEWS%>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.DS_OGGETTO%>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.DL_TESTO%>" colSpan="4" controlWidth="w60" /> <sl:newLine />
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.DT_INI_PUBBLIC%>" colSpan="4" controlWidth="w20"/> <sl:newLine />
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.DT_FIN_PUBBLIC%>" colSpan="4" controlWidth="w20"/> <sl:newLine />
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.FL_PUBBLIC_LOGIN%>" colSpan="4" controlWidth="w20"/> <sl:newLine />
                <slf:lblField name="<%=GestioneNewsForm.DettaglioNews.FL_PUBBLIC_HOMEPAGE%>" colSpan="4" controlWidth="w20"/> <sl:newLine />
            </slf:fieldSet> 

            <sl:newLine skipLine="true"/>
            <slf:lblField name="<%=GestioneNewsForm.ApplicazioniFields.NM_APPLIC%>" colSpan="2" />
            <slf:lblField name="<%=GestioneNewsForm.ApplicazioniFields.DS_APPLIC%>" colSpan="2" />

            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField  name="<%=GestioneNewsForm.ApplicazioniFields.AGGIUNGI_APPLICAZIONE%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <slf:list name="<%= GestioneNewsForm.ApplicazioniList.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>