package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the APL_V_LIS_ORGANIZ_USO_SIS_VERS database table.
 */
@Entity
@Table(name = "APL_V_LIS_ORGANIZ_USO_SIS_VERS")
@NamedQuery(name = "AplVLisOrganizUsoSisVers.findAll", query = "SELECT a FROM AplVLisOrganizUsoSisVers a")
public class AplVLisOrganizUsoSisVers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private BigDecimal idOrganizApplic;

    private String nmApplic;

    private String nmSistemaVersante;

    public AplVLisOrganizUsoSisVers() {
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
    }

    private AplVLisOrganizUsoSisVersId aplVLisOrganizUsoSisVersId;

    @EmbeddedId()
    public AplVLisOrganizUsoSisVersId getAplVLisOrganizUsoSisVersId() {
        return aplVLisOrganizUsoSisVersId;
    }

    public void setAplVLisOrganizUsoSisVersId(AplVLisOrganizUsoSisVersId aplVLisOrganizUsoSisVersId) {
        this.aplVLisOrganizUsoSisVersId = aplVLisOrganizUsoSisVersId;
    }
}
