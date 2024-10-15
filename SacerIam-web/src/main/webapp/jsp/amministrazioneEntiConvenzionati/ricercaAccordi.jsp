<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DESCRIPTION%>" >
        <style>
            .personalizza {               
                width: 10px;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
            }

            .personalizza:hover{
                overflow: visible; 
                white-space: normal; 
                width: auto;               
            }
            
           
        </style>

        <script type="text/javascript">
            $(document).ready(function () {
                $('#ListaAccordi tr').each(
                        function (index) {
                            if(index % 2 === 1){
                                $(this).css({"background-color": "#EFEFEF"});
                            }
                            var elemento = $(this).find('td:eq(15)');
                            elemento.css({"overflow": "hidden", "text-overflow": "ellipsis", "width": "200px", "white-space": "nowrap","display":"block"});
                            
                });
                
                initChangeEvents();
            });

            function initChangeEvents() {
            $('#Fl_esistono_sae_combo').change(function () {
                var input = $(this).val();
                if (input === '0') {
                    $('#Sae_da').prop("disabled", true);
                    $('#Sae_a').prop("disabled", true);
                    $('#Sae_da').val("");
                    $('#Sae_a').val("");
                } else {
                    $('#Sae_da').prop("disabled", false);
                    $('#Sae_a').prop("disabled", false);
                }
            });       
            
            $('#Fl_esistono_sue_combo').change(function () {
                var input = $(this).val();
                if (input === '0') {
                    $('#Sue_da').prop("disabled", true);
                    $('#Sue_a').prop("disabled", true);
                    $('#Sue_da').val("");
                    $('#Sue_a').val("");
                } else {
                    $('#Sue_da').prop("disabled", false);
                    $('#Sue_a').prop("disabled", false);
                }
            });
                        
            };

        </script>
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.ID_ACCORDO_ENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.CD_REGISTRO_REPERTORIO%>" colSpan="3"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.AA_REPERTORIO%>" colSpan="3"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.CD_KEY_REPERTORIO%>" colSpan="3"/><sl:newLine />                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.ID_TIPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_DEC_ACCORDO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_DEC_ACCORDO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_FINE_VALID_ACCORDO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_FINE_VALID_ACCORDO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_DEC_ACCORDO_INFO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_DEC_ACCORDO_INFO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_SCAD_ACCORDO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.DT_SCAD_ACCORDO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.FL_RECESSO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.ID_TIPO_GESTIONE_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.TI_STATO_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.FL_ESISTE_NOTA_FATTURAZIONE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.FL_FASCIA_MANUALE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.FL_ESISTONO_SAE_COMBO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.SAE_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.SAE_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.FL_ESISTONO_SUE_COMBO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.SUE_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.SUE_A%>" colSpan="2"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.RICERCA_ACCORDI%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.PULISCI_RICERCA_ACCORDI%>" width="w25"/>                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.ANNUALITA_SENZA_ATTO_ACCORDI%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAccordi.REPORT_STORAGE_EXTRA_RER%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ListaAccordi.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.ListaAccordi.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ListaAccordi.NAME%>" />
            <sl:newLine skipLine="true"/>    
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoSection.NAME%>" styleClass="importantContainer">
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoList.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoList.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoList.NAME%>" />
            </slf:section>            <!--<!-- comment -->
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ReportStorageExtraRerSection.NAME%>" styleClass="importantContainer">
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ReportStorageExtraRerList.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.ReportStorageExtraRerList.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ReportStorageExtraRerList.NAME%>" />
            </slf:section>   
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
