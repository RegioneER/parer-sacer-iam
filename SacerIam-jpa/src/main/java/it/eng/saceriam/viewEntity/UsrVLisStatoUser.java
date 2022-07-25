package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the USR_V_LIS_STATO_USER database table.
 *
 */
@Entity
@Table(name = "USR_V_LIS_STATO_USER")
@NamedQuery(name = "UsrVLisStatoUser.findAll", query = "SELECT u FROM UsrVLisStatoUser u")
public class UsrVLisStatoUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idRich;
    private BigDecimal idStatoUser;
    private String idUnitaDocRich;
    private BigDecimal idUserIam;
    private String keyRichGestUser;
    private String listaTipiAzione;
    private String tiStatoUser;
    private Timestamp tsStato;

    public UsrVLisStatoUser() {
    }

    @Column(name = "ID_RICH")
    public String getIdRich() {
        return this.idRich;
    }

    public void setIdRich(String idRich) {
        this.idRich = idRich;
    }

    @Id
    @Column(name = "ID_STATO_USER")
    public BigDecimal getIdStatoUser() {
        return this.idStatoUser;
    }

    public void setIdStatoUser(BigDecimal idStatoUser) {
        this.idStatoUser = idStatoUser;
    }

    @Column(name = "ID_UNITA_DOC_RICH")
    public String getIdUnitaDocRich() {
        return this.idUnitaDocRich;
    }

    public void setIdUnitaDocRich(String idUnitaDocRich) {
        this.idUnitaDocRich = idUnitaDocRich;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

    @Column(name = "KEY_RICH_GEST_USER")
    public String getKeyRichGestUser() {
        return this.keyRichGestUser;
    }

    public void setKeyRichGestUser(String keyRichGestUser) {
        this.keyRichGestUser = keyRichGestUser;
    }

    @Column(name = "LISTA_TIPI_AZIONE")
    public String getListaTipiAzione() {
        return this.listaTipiAzione;
    }

    public void setListaTipiAzione(String listaTipiAzione) {
        this.listaTipiAzione = listaTipiAzione;
    }

    @Column(name = "TI_STATO_USER")
    public String getTiStatoUser() {
        return this.tiStatoUser;
    }

    public void setTiStatoUser(String tiStatoUser) {
        this.tiStatoUser = tiStatoUser;
    }

    @Column(name = "TS_STATO")
    public Timestamp getTsStato() {
        return this.tsStato;
    }

    public void setTsStato(Timestamp tsStato) {
        this.tsStato = tsStato;
    }

}
