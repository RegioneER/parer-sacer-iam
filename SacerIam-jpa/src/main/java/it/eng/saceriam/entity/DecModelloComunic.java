package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the DEC_MODELLO_COMUNIC database table.
 * 
 */
@Entity
@Table(name = "DEC_MODELLO_COMUNIC")
@NamedQuery(name = "DecModelloComunic.findAll", query = "SELECT d FROM DecModelloComunic d")
public class DecModelloComunic implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idModelloComunic;
    private String blTestoComunic;
    private String cdModelloComunic;
    private String dsModelloComunic;
    private String dsOggettoComunic;
    private Date dtIstituz;
    private Date dtSoppres;
    private String nmMittente;
    private String tiComunic;
    private String tiOggettoQuery;
    private String tiStatoTrigComunic;
    private List<NtfNotifica> ntfNotificas = new ArrayList<>();

    public DecModelloComunic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SDEC_MODELLO_COMUNIC") // @SequenceGenerator(name =
                                                                          // "DEC_MODELLO_COMUNIC_IDMODELLOCOMUNIC_GENERATOR",
                                                                          // sequenceName = "SDEC_MODELLO_COMUNIC",
                                                                          // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEC_MODELLO_COMUNIC_IDMODELLOCOMUNIC_GENERATOR")
    @Column(name = "ID_MODELLO_COMUNIC")
    public Long getIdModelloComunic() {
        return this.idModelloComunic;
    }

    public void setIdModelloComunic(Long idModelloComunic) {
        this.idModelloComunic = idModelloComunic;
    }

    @Lob
    @Column(name = "BL_TESTO_COMUNIC")
    public String getBlTestoComunic() {
        return this.blTestoComunic;
    }

    public void setBlTestoComunic(String blTestoComunic) {
        this.blTestoComunic = blTestoComunic;
    }

    @Column(name = "CD_MODELLO_COMUNIC")
    public String getCdModelloComunic() {
        return this.cdModelloComunic;
    }

    public void setCdModelloComunic(String cdModelloComunic) {
        this.cdModelloComunic = cdModelloComunic;
    }

    @Column(name = "DS_MODELLO_COMUNIC")
    public String getDsModelloComunic() {
        return this.dsModelloComunic;
    }

    public void setDsModelloComunic(String dsModelloComunic) {
        this.dsModelloComunic = dsModelloComunic;
    }

    @Column(name = "DS_OGGETTO_COMUNIC")
    public String getDsOggettoComunic() {
        return this.dsOggettoComunic;
    }

    public void setDsOggettoComunic(String dsOggettoComunic) {
        this.dsOggettoComunic = dsOggettoComunic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ISTITUZ")
    public Date getDtIstituz() {
        return this.dtIstituz;
    }

    public void setDtIstituz(Date dtIstituz) {
        this.dtIstituz = dtIstituz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOPPRES")
    public Date getDtSoppres() {
        return this.dtSoppres;
    }

    public void setDtSoppres(Date dtSoppres) {
        this.dtSoppres = dtSoppres;
    }

    @Column(name = "NM_MITTENTE")
    public String getNmMittente() {
        return this.nmMittente;
    }

    public void setNmMittente(String nmMittente) {
        this.nmMittente = nmMittente;
    }

    @Column(name = "TI_COMUNIC")
    public String getTiComunic() {
        return this.tiComunic;
    }

    public void setTiComunic(String tiComunic) {
        this.tiComunic = tiComunic;
    }

    @Column(name = "TI_OGGETTO_QUERY")
    public String getTiOggettoQuery() {
        return this.tiOggettoQuery;
    }

    public void setTiOggettoQuery(String tiOggettoQuery) {
        this.tiOggettoQuery = tiOggettoQuery;
    }

    @Column(name = "TI_STATO_TRIG_COMUNIC")
    public String getTiStatoTrigComunic() {
        return this.tiStatoTrigComunic;
    }

    public void setTiStatoTrigComunic(String tiStatoTrigComunic) {
        this.tiStatoTrigComunic = tiStatoTrigComunic;
    }

    // bi-directional many-to-one association to NtfNotifica
    @OneToMany(mappedBy = "decModelloComunic")
    public List<NtfNotifica> getNtfNotificas() {
        return this.ntfNotificas;
    }

    public void setNtfNotificas(List<NtfNotifica> ntfNotificas) {
        this.ntfNotificas = ntfNotificas;
    }

    public NtfNotifica addNtfNotifica(NtfNotifica ntfNotifica) {
        getNtfNotificas().add(ntfNotifica);
        ntfNotifica.setDecModelloComunic(this);

        return ntfNotifica;
    }

    public NtfNotifica removeNtfNotifica(NtfNotifica ntfNotifica) {
        getNtfNotificas().remove(ntfNotifica);
        ntfNotifica.setDecModelloComunic(null);

        return ntfNotifica;
    }

}