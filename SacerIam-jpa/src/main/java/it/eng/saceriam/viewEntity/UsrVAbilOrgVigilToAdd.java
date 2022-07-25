package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_ORG_VIGIL_TO_ADD database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ORG_VIGIL_TO_ADD")
@NamedQuery(name = "UsrVAbilOrgVigilToAdd.findAll", query = "SELECT u FROM UsrVAbilOrgVigilToAdd u")
public class UsrVAbilOrgVigilToAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteProdutVigil;

    private BigDecimal idUsoUserApplicGestito;

    private BigDecimal idVigilEnteProdut;

    private String nmApplic;

    public UsrVAbilOrgVigilToAdd() {
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_ENTE_PRODUT_VIGIL")
    public BigDecimal getIdEnteProdutVigil() {
        return this.idEnteProdutVigil;
    }

    public void setIdEnteProdutVigil(BigDecimal idEnteProdutVigil) {
        this.idEnteProdutVigil = idEnteProdutVigil;
    }

    @Column(name = "ID_USO_USER_APPLIC_GESTITO")
    public BigDecimal getIdUsoUserApplicGestito() {
        return this.idUsoUserApplicGestito;
    }

    public void setIdUsoUserApplicGestito(BigDecimal idUsoUserApplicGestito) {
        this.idUsoUserApplicGestito = idUsoUserApplicGestito;
    }

    @Column(name = "ID_VIGIL_ENTE_PRODUT")
    public BigDecimal getIdVigilEnteProdut() {
        return this.idVigilEnteProdut;
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
        this.idVigilEnteProdut = idVigilEnteProdut;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVAbilOrgVigilToAddId usrVAbilOrgVigilToAddId;

    @EmbeddedId()
    public UsrVAbilOrgVigilToAddId getUsrVAbilOrgVigilToAddId() {
        return usrVAbilOrgVigilToAddId;
    }

    public void setUsrVAbilOrgVigilToAddId(UsrVAbilOrgVigilToAddId usrVAbilOrgVigilToAddId) {
        this.usrVAbilOrgVigilToAddId = usrVAbilOrgVigilToAddId;
    }
}
