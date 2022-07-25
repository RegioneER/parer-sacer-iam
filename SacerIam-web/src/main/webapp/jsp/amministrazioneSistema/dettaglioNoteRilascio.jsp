<%@ page import="it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Ricerca note di rilascio" ></sl:head>
    <sl:body>
        <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaNoteRilascio'].status eq 'view') }">
            <c:set var="legend" value="Novità della versione ${sessionScope['###_FORM_CONTAINER']['dettaglioNoteRilascio']['cd_versione'].value} del ${sessionScope['###_FORM_CONTAINER']['dettaglioNoteRilascio']['dt_versione'].value}" />
        </c:if>
        <sl:header showChangeOrganizationBtn="false"/>
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>

            <slf:messageBox />    
            <sl:contentTitle title="Dettaglio note di rilascio"/>

            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaNoteRilascio'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= GestioneNoteRilascioForm.DettaglioNoteRilascio.NAME%>" hideBackButton="true" /> 
            </c:if>   

            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaNoteRilascio'].table['empty']) }">
                <slf:listNavBarDetail name="<%= GestioneNoteRilascioForm.ListaNoteRilascio.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true"/>
            <c:choose>
                <c:when test="${!empty showNotaRilascio || !(sessionScope['###_FORM_CONTAINER']['listaNoteRilascio'].status eq 'view')}">  
                    <slf:fieldSet legend="${legend}">
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.ID_NOTA_RILASCIO %>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                        <c:choose>
                            <c:when test="${(sessionScope['###_FORM_CONTAINER']['listaNoteRilascio'].status eq 'view')}">
                                <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.NM_APPLIC%>" colSpan="4" controlWidth="w40"/><sl:newLine />
                            </c:when>
                            <c:otherwise>
                                <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.ID_APPLIC%>" colSpan="4" controlWidth="w40"/><sl:newLine />
                            </c:otherwise>
                        </c:choose>
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.CD_VERSIONE%>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.DT_VERSIONE%>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.DT_INI_VAL%>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.DT_FINE_VAL%>" colSpan="4" controlWidth="w40"/> <sl:newLine />
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.DS_EVIDENZA%>" colSpan="4" controlWidth="w60" /> <sl:newLine />
                        <slf:lblField name="<%=GestioneNoteRilascioForm.DettaglioNoteRilascio.BL_NOTA%>" colSpan="4" controlWidth="w60"/> <sl:newLine />
                    </slf:fieldSet> 
                </c:when>
                <c:otherwise>
                    <slf:fieldSet legend="${legend}">
                        <p style="text-align:left; word-wrap: break-word;"><c:out value="${infoDaMostrare}" escapeXml="false" />                   
                    </slf:fieldSet>
                </c:otherwise>
            </c:choose>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaNoteRilascio'].status eq 'view') && empty sessionScope.isFromNotaRilascioPrec}">
                <slf:section name="<%=GestioneNoteRilascioForm.NoteRilascioPrecSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= GestioneNoteRilascioForm.NoteRilascioPrecedentiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= GestioneNoteRilascioForm.NoteRilascioPrecedentiList.NAME%>" />
                    <slf:listNavBar  name="<%= GestioneNoteRilascioForm.NoteRilascioPrecedentiList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>