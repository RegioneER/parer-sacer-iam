<%@ page import="it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=GestioneFatturazioneServiziForm.FiltriFattura.DESCRIPTION%>" >
        <script type="text/javascript">
                // Quando ha finito di disegnare tutta la pagina
                $(document).ready(function(){  
                    //$('#spagoLiteAppForm').on('submit', function(){ this.submit(); $('.overlay').hide();});
                    $("[name='operation__estraiRigheFattureTotali']").click(function() {
                        //alert( "Handler for .click() called." );
                        $('.overlay').hide();
                    });
                    $("[name='operation__estraiRigheFattureDaRicerca']").click(function() {
                        //alert( "Handler for .click() called." );
                        $('.overlay').hide();
                    });
                });
        </script>
    </sl:head>
        
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=GestioneFatturazioneServiziForm.FiltriFattura.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                                
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.CD_CLIENTE_SAP%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.ID_TIPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.ID_TIPO_SERVIZIO%>" colSpan="2"/><sl:newLine />
                <slf:section name="<%=GestioneFatturazioneServiziForm.NumFatturaTempSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.AA_FATTURA_DA%>" colSpan="2" controlWidth="w20" />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.AA_FATTURA_A%>" colSpan="2"controlWidth="w20" /><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.PG_FATTURA_ENTE_DA%>" colSpan="2"/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.PG_FATTURA_ENTE_A%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.CD_FATTURA%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.NumFatturaSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.CD_REGISTRO_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.AA_EMISS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.CD_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.PG_FATTURA_EMIS%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.DT_EMIS_FATTURA_DA%>" colSpan="2" controlWidth="w20"/>
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.DT_EMIS_FATTURA_A%>" colSpan="2" controlWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.TI_STATO_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.RICERCA_FATTURA%>" width="w25"/>
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.CAMBIA_STATO_FATTURE%>" width="w25"/>
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.ESTRAI_RIGHE_FATTURE_TOTALI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=GestioneFatturazioneServiziForm.ListaFatture.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=GestioneFatturazioneServiziForm.ListaFatture.NAME%>" />
            <slf:listNavBar  name="<%=GestioneFatturazioneServiziForm.ListaFatture.NAME%>" />
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>                
                <slf:lblField name="<%=GestioneFatturazioneServiziForm.FiltriFattura.ESTRAI_RIGHE_FATTURE_DA_RICERCA%>" width="w25"/>
            </sl:pulsantiera>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
