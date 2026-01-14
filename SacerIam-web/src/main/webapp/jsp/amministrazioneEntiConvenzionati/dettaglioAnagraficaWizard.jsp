<%--
 Engineering Ingegneria Informatica S.p.A.

 Copyright (C) 2023 Regione Emilia-Romagna
 <p/>
 This program is free software: you can redistribute it and/or modify it under the terms of
 the GNU Affero General Public License as published by the Free Software Foundation,
 either version 3 of the License, or (at your option) any later version.
 <p/>
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Affero General Public License for more details.
 <p/>
 You should have received a copy of the GNU Affero General Public License along with this program.
 If not, see <https://www.gnu.org/licenses/>.
 --%>

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.InserimentoWizard.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <slf:wizard name="<%= AmministrazioneEntiConvenzionatiForm.InserimentoWizard.NAME%>">
                <slf:wizardNavBar name="<%=AmministrazioneEntiConvenzionatiForm.InserimentoWizard.NAME%>" />
                <slf:step name="<%= AmministrazioneEntiConvenzionatiForm.InserimentoWizard.STEP1%>" >
                    <slf:fieldSet borderHidden="true">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.NM_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DT_FINE_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.ID_ENTE_CONVENZ_NUOVO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
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
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DS_CATEG_ENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AmbitoTerritorialeSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_AMBITO_TERRIT_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_AMBITO_TERRIT_PROVINCIA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficaDetail.DL_NOTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    </slf:fieldSet>      
                </slf:step>

                <slf:step name="<%= AmministrazioneEntiConvenzionatiForm.InserimentoWizard.STEP2%>" >
                    <slf:fieldSet borderHidden="true">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ_FIELD%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_INI_VAL_APPART_AMBIENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_FIN_VAL_APPART_AMBIENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />--%>
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_CONVENZ_NUOVO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine skipLine="true"/>
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.SedeLegaleSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_VIA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_CAP_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_CITTA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_PROV_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_NAZIONE_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </slf:section>
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_FISC%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_CATEG_ENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AmbitoTerritorialeSection.NAME%>" styleClass="importantContainer w100">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_AMBITO_TERRIT_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_AMBITO_TERRIT_PROVINCIA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </slf:section>
                    </slf:fieldSet>
                </slf:step>
            </slf:wizard>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
