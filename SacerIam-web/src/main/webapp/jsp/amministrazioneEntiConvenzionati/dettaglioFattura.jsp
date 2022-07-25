<%@ page import="it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=GestioneFatturazioneServiziForm.FatturaDetail.DESCRIPTION%>" >
        <script type="text/javascript" src="<c:url value='/js/customFatturaMessageBox.js'/>" ></script>
        <style>            
            #ListaLinkFatturaEmessa { 
                background-color: #D3101C;
                border: none !important;
            }
            #ListaLinkFatturaEmessa td {
                background-color: #D3101C;                
            }            
            a.FatturaEmessa{                               
               /* per eventuale personalizzazione */
            }
            #dummyText {
                opacity: 0;
                position: absolute;
                top: 0;
                left: 0;    
            }
        </style>

        <script type="text/javascript" >
            $(document).ready(function () {
                $('div[id^="Fl_superamento_scaglione"] > img[src$="checkbox-off.png"]').attr("src", "./img/alternative/checkbox-on.png");
                $('div[id^="Fl_superamento_scaglione"] > img[src$="checkbox-warn.png"]').remove();
            });

        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />

            <c:if test="${!empty requestScope.customBoxSalvataggioFatturaControllo1}">
                <div class="messages customBoxSalvataggioFatturaControllo1 ">
                    <ul>
                        <li class="message info "><c:out value="${requestScope.customMessageSalvataggioFattura}"/> </li>
                    </ul>                   
                </div>
            </c:if>

            <c:if test="${!empty requestScope.customBoxSalvataggioFatturaControllo2}">
                <div class="messages customBoxSalvataggioFatturaControllo2 ">
                    <ul>
                        <li class="message info "><c:out value="${requestScope.customMessageSalvataggioFattura}"/> </li>
                    </ul>                   
                </div>
            </c:if>
            
            <c:if test="${!empty requestScope.customRiemissioneFattureStornateMessageBox}">
                <div class="messages customRiemissioneFattureStornateDaFattura ">
                    <div class="containerLeft w4ctr">      
                        <sl:newLine />
                        <slf:lblField name="<%=GestioneFatturazioneServiziForm.RiemissioneFattureFields.ANNO_TESTATA_STORN%>" width="w100" labelWidth="w40" controlWidth="w30" />
                    </div>
                </div>
            </c:if>
            
              <c:if test="${!empty requestScope.customBoxSalvataggioFatturaControlloImporti}">
                <div class="messages customBoxSalvataggioFatturaControlloImporti ">
                    <ul>
                        <li class="message info "><c:out value="${requestScope.customMessageSalvataggioFattura}"/> </li>
                    </ul>                   
                </div>
            </c:if>

            <c:if test="${!empty requestScope.customBoxSalvataggioFatturaInserimentoDataNote}">
                <div class="messages customBoxSalvataggioFatturaInserimentoDataNote ">
                    <div class="containerLeft w4ctr">        
                        <!-- Campo di input fittizio per evitare che all'apertura del popup il datepicker risulti già aperto -->
                        <input type="text" id="dummyText" /> 
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaPopUpFields.DATA_MODIFICA%>" width="w100" controlWidth="w60" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField  name="<%=GestioneFatturazioneServiziForm.FatturaPopUpFields.NOTE_MODIFICA%>" width="w100" controlWidth="w60" />
                        <sl:newLine skipLine="true"/>
                        <p><c:out value="${requestScope.customMessageSalvataggioFattura}"/></p>                        
                    </div>
                </div>
            </c:if>

            <%--<div class="pulsantieraSalvataggioFattura">
            <sl:pulsantiera >
                <slf:buttonList name="<%= GestioneFatturazioneServiziForm.SalvaStrutturaCustomMessageButtonList.NAME%>"/>
            </sl:pulsantiera>
            </div>--%>

            <sl:newLine skipLine="true"/>      
            <sl:contentTitle title="<%=GestioneFatturazioneServiziForm.FatturaDetail.DESCRIPTION%>" />
            <c:choose>
                <c:when test='<%= session.getAttribute("navTableFatture").equals(GestioneFatturazioneServiziForm.ListaFatture.NAME)%>'>
                    <c:if test="${sessionScope['###_FORM_CONTAINER']['listaFatture'].table['empty']}">
                        <slf:fieldBarDetailTag name="<%= GestioneFatturazioneServiziForm.FatturaDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaFatture'].status eq 'insert'}"/> 
                    </c:if>   
                    <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaFatture'].table['empty']) }">
                        <slf:listNavBarDetail name="<%= GestioneFatturazioneServiziForm.ListaFatture.NAME%>" />  
                    </c:if>
                </c:when>
                <c:otherwise>
                    <slf:listNavBarDetail name="<%= GestioneFatturazioneServiziForm.ListaDetailFattureRiemesse.NAME%>" />
                </c:otherwise>
            </c:choose>

            <sl:newLine skipLine="true"/>
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.ID_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.ID_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=GestioneFatturazioneServiziForm.EnteConvenzionatoSection.NAME%>" >                    
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CODICE_ENTE%>" colSpan="2"/><sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_FISC%>" colSpan="2"/><sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CLIENTE_SAP%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CATEG_ENTE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.DatiAccordoSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_TIPO_ACCORDO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_UFE%>" colSpan="2"/>
                    <sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_DEC_ACCORDO_INFO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DS_UFE%>" colSpan="2"/>
                    <sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_SCAD_ACCORDO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DS_IVA%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_DEC_ACCORDO%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_FINE_VALID_ACCORDO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_IVA%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.FL_PAGAMENTO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CIG%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_TARIFFARIO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CUP%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CLASSE_ENTE_CONVENZ%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_RIF_CONTAB%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NI_ABITANTI%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_COGE%>" colSpan="2"/>
                    <sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DS_NOTE_ACCORDO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CAPITOLO%>" colSpan="2"/>
                    <sl:newLine/>
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.StatoFatturaSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.TI_STATO_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_REG_STATO_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.MODIFICHE_INTERVENUTE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.DatiFatturaTemporaneaSection.NAME%>" >                    
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.AA_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.PG_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_CREAZIONE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.DatiFatturaEmessaSection.NAME%>" >
                    <c:if test="${(!empty requestScope.linkFatturaEmessa) && (sessionScope['###_FORM_CONTAINER']['listaFatture'].status eq 'view')}" >
                        <div class="slLabel wlbl" >&nbsp;</div>
                        <div class="containerLeft w2ctr"></div>
                        <%--<slf:container width="w12" >
                            <slf:list name="<%= GestioneFatturazioneServiziForm.ListaLinkFatturaEmessa.NAME%>" />
                        </slf:container>--%>
                        
                        <a title="Visualizza unità doc" class="linkButton FatturaEmessa" href="${sessionScope.externalLinkParamApplic}" target="_blank">Vai all'unità documentaria</a>
                        <sl:newLine skipLine="true"/>
                    </c:if>
                    <sl:newLine />
                     <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_ENTE%>" colSpan="2"/>
                    <sl:newLine />
                     <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_STRUT%>" colSpan="2"/>
                    <sl:newLine />
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaFatture'].status eq 'update') }">
                            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_REGISTRO_EMIS_FATTURA_TEXT%>" colSpan="2"/>
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_REGISTRO_EMIS_FATTURA_COMBO%>" colSpan="2"/>
                        </c:otherwise>                         
                    </c:choose>       
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.UdButtonList.SCARICA_COMP_FILE_UD_FATTURA_EMESSA%>" colSpan="2"/>
                    <sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.AA_EMISS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <%--<slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.PG_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />--%>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_SCAD_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_FATTURA_SAP%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NT_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.ImportiSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.FL_SUPERAMENTO_SCAGLIONE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_NETTO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_IVA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_DA_PAGARE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_PAGAM%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.OperazioniStornoSection.NAME%>" >
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaFatture'].status eq 'update') }">
                            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_REGISTRO_EMIS_NOTA_CREDITO_TEXT%>" colSpan="2"/>
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_REGISTRO_EMIS_NOTA_CREDITO_COMBO%>" colSpan="2"/>
                        </c:otherwise>
                    </c:choose>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.UdButtonList.SCARICA_COMP_FILE_UD_STORNO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.AA_EMISS_NOTA_CREDITO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_EMIS_NOTA_CREDITO%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_EMIS_NOTA_CREDITO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.FL_DA_RIEMIS%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NT_EMIS_NOTA_CREDITO%>" colSpan="2"/><sl:newLine />
                </slf:section>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.RiemissioneFattureFields.RIEMETTI_FATTURE_STORNATE%>" width="w25"/>
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.LOG_EVENTI%>" colSpan="2"/><sl:newLine />
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaFatture'].status eq 'view') 
                          && (sessionScope['###_FORM_CONTAINER']['listaFattureRiemesse'].status eq 'view') 
                          && (sessionScope['###_FORM_CONTAINER']['listaDetailFattureRiemesse'].status eq 'view')}">
                <slf:section name="<%=GestioneFatturazioneServiziForm.ServiziFatturatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= GestioneFatturazioneServiziForm.ListaServiziFatturati.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= GestioneFatturazioneServiziForm.ListaServiziFatturati.NAME%>" />
                    <slf:listNavBar  name="<%= GestioneFatturazioneServiziForm.ListaServiziFatturati.NAME%>" />
                </slf:section>               
                <sl:newLine skipLine="true"/>    
                <slf:section name="<%=GestioneFatturazioneServiziForm.IncassiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= GestioneFatturazioneServiziForm.ListaIncassi.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= GestioneFatturazioneServiziForm.ListaIncassi.NAME%>" />
                    <slf:listNavBar  name="<%= GestioneFatturazioneServiziForm.ListaIncassi.NAME%>" />
                </slf:section>               
                <sl:newLine skipLine="true"/>    
                <slf:section name="<%=GestioneFatturazioneServiziForm.FattureRiemesseSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= GestioneFatturazioneServiziForm.ListaFattureRiemesse.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= GestioneFatturazioneServiziForm.ListaFattureRiemesse.NAME%>" />
                    <slf:listNavBar  name="<%= GestioneFatturazioneServiziForm.ListaFattureRiemesse.NAME%>" />
                </slf:section>    
                <sl:newLine skipLine="true"/>    
                <slf:section name="<%=GestioneFatturazioneServiziForm.SollecitiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= GestioneFatturazioneServiziForm.ListaSolleciti.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= GestioneFatturazioneServiziForm.ListaSolleciti.NAME%>" />
                    <slf:listNavBar  name="<%= GestioneFatturazioneServiziForm.ListaSolleciti.NAME%>" />
                </slf:section>    
                <sl:newLine skipLine="true"/>    
                <slf:section name="<%=GestioneFatturazioneServiziForm.ModificheSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= GestioneFatturazioneServiziForm.ListaModifiche.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= GestioneFatturazioneServiziForm.ListaModifiche.NAME%>" />
                    <slf:listNavBar  name="<%= GestioneFatturazioneServiziForm.ListaModifiche.NAME%>" />
                </slf:section>    
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
