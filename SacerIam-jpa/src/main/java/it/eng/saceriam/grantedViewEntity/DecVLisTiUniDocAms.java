package it.eng.saceriam.grantedViewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the DEC_V_LIS_TI_UNI_DOC_AMS database table.
 * 
 */
@Entity
@Table(schema = "SACER", name = "DEC_V_LIS_TI_UNI_DOC_AMS")
public class DecVLisTiUniDocAms implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cdRegistroUnitaDoc;
    private BigDecimal idRegistroUnitaDoc;
    private BigDecimal idTipoUnitaDoc;
    private BigDecimal idTipoUnitaDocAmmesso;
    private String nmTipoUnitaDoc;

    public DecVLisTiUniDocAms() {
    }

    @Column(name = "CD_REGISTRO_UNITA_DOC")
    public String getCdRegistroUnitaDoc() {
        return this.cdRegistroUnitaDoc;
    }

    public void setCdRegistroUnitaDoc(String cdRegistroUnitaDoc) {
        this.cdRegistroUnitaDoc = cdRegistroUnitaDoc;
    }

    @Column(name = "ID_REGISTRO_UNITA_DOC")
    public BigDecimal getIdRegistroUnitaDoc() {
        return this.idRegistroUnitaDoc;
    }

    public void setIdRegistroUnitaDoc(BigDecimal idRegistroUnitaDoc) {
        this.idRegistroUnitaDoc = idRegistroUnitaDoc;
    }

    @Column(name = "ID_TIPO_UNITA_DOC")
    public BigDecimal getIdTipoUnitaDoc() {
        return this.idTipoUnitaDoc;
    }

    public void setIdTipoUnitaDoc(BigDecimal idTipoUnitaDoc) {
        this.idTipoUnitaDoc = idTipoUnitaDoc;
    }

    @Id
    @Column(name = "ID_TIPO_UNITA_DOC_AMMESSO")
    public BigDecimal getIdTipoUnitaDocAmmesso() {
        return this.idTipoUnitaDocAmmesso;
    }

    public void setIdTipoUnitaDocAmmesso(BigDecimal idTipoUnitaDocAmmesso) {
        this.idTipoUnitaDocAmmesso = idTipoUnitaDocAmmesso;
    }

    @Column(name = "NM_TIPO_UNITA_DOC")
    public String getNmTipoUnitaDoc() {
        return this.nmTipoUnitaDoc;
    }

    public void setNmTipoUnitaDoc(String nmTipoUnitaDoc) {
        this.nmTipoUnitaDoc = nmTipoUnitaDoc;
    }

}