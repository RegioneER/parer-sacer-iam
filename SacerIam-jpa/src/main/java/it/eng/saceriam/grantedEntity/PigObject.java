package it.eng.saceriam.grantedEntity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the PIG_OBJECT database table.
 *
 */
@Entity
@Table(schema = "SACER_PING", name = "PIG_OBJECT")
public class PigObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idObject;
    private String cdKeyObject;
    private String cdTrasf;
    private String cdVersGen;
    private String cdVersioneTrasf;
    private String dsObject;
    private IamUser iamUser;
    private PigVers pigVer;
    private String tiPriorita;

    public PigObject() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SPIG_OBJECT")
    @Column(name = "ID_OBJECT")
    public long getIdObject() {
        return this.idObject;
    }

    public void setIdObject(long idObject) {
        this.idObject = idObject;
    }

    @Column(name = "CD_KEY_OBJECT")
    public String getCdKeyObject() {
        return this.cdKeyObject;
    }

    public void setCdKeyObject(String cdKeyObject) {
        this.cdKeyObject = cdKeyObject;
    }

    @Column(name = "CD_TRASF")
    public String getCdTrasf() {
        return this.cdTrasf;
    }

    public void setCdTrasf(String cdTrasf) {
        this.cdTrasf = cdTrasf;
    }

    @Column(name = "CD_VERS_GEN")
    public String getCdVersGen() {
        return this.cdVersGen;
    }

    public void setCdVersGen(String cdVersGen) {
        this.cdVersGen = cdVersGen;
    }

    @Column(name = "CD_VERSIONE_TRASF")
    public String getCdVersioneTrasf() {
        return this.cdVersioneTrasf;
    }

    public void setCdVersioneTrasf(String cdVersioneTrasf) {
        this.cdVersioneTrasf = cdVersioneTrasf;
    }

    @Column(name = "DS_OBJECT")
    public String getDsObject() {
        return this.dsObject;
    }

    public void setDsObject(String dsObject) {
        this.dsObject = dsObject;
    }

    // bi-directional many-to-one association to IamUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM")
    public IamUser getIamUser() {
        return this.iamUser;
    }

    public void setIamUser(IamUser iamUser) {
        this.iamUser = iamUser;
    }

    // bi-directional many-to-one association to PigVer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VERS")
    public PigVers getPigVer() {
        return this.pigVer;
    }

    public void setPigVer(PigVers pigVer) {
        this.pigVer = pigVer;
    }

    @Column(name = "TI_PRIORITA")
    public String getTiPriorita() {
        return this.tiPriorita;
    }

    public void setTiPriorita(String tiPriorita) {
        this.tiPriorita = tiPriorita;
    }

}
