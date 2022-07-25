package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ALLORGCHILD_BY_PRODUT database table.
 */
@Entity
@Table(name = "USR_V_ALLORGCHILD_BY_PRODUT")
@NamedQuery(name = "UsrVAllorgchildByProdut.findAll", query = "SELECT u FROM UsrVAllorgchildByProdut u")
public class UsrVAllorgchildByProdut implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idApplic;

    private BigDecimal idEnteProdut;

    private BigDecimal idEnteProdutColleg;

    private String nmApplic;

    public UsrVAllorgchildByProdut() {
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_ENTE_PRODUT")
    public BigDecimal getIdEnteProdut() {
        return this.idEnteProdut;
    }

    public void setIdEnteProdut(BigDecimal idEnteProdut) {
        this.idEnteProdut = idEnteProdut;
    }

    @Column(name = "ID_ENTE_PRODUT_COLLEG")
    public BigDecimal getIdEnteProdutColleg() {
        return this.idEnteProdutColleg;
    }

    public void setIdEnteProdutColleg(BigDecimal idEnteProdutColleg) {
        this.idEnteProdutColleg = idEnteProdutColleg;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVAllorgchildByProdutId usrVAllorgchildByProdutId;

    @EmbeddedId()
    public UsrVAllorgchildByProdutId getUsrVAllorgchildByProdutId() {
        return usrVAllorgchildByProdutId;
    }

    public void setUsrVAllorgchildByProdutId(UsrVAllorgchildByProdutId usrVAllorgchildByProdutId) {
        this.usrVAllorgchildByProdutId = usrVAllorgchildByProdutId;
    }
}
