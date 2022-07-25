package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHECK_RUOLO_DEFAULT database table.
 */
@Entity
@Table(name = "USR_V_CHECK_RUOLO_DEFAULT")
public class UsrVCheckRuoloDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutorAggiunta;

    private BigDecimal idApplicScelta;

    private String nmAutorAggiunta;

    public UsrVCheckRuoloDefault() {
    }

    @Column(name = "DS_AUTOR_AGGIUNTA")
    public String getDsAutorAggiunta() {
        return this.dsAutorAggiunta;
    }

    public void setDsAutorAggiunta(String dsAutorAggiunta) {
        this.dsAutorAggiunta = dsAutorAggiunta;
    }

    @Column(name = "ID_APPLIC_SCELTA")
    public BigDecimal getIdApplicScelta() {
        return this.idApplicScelta;
    }

    public void setIdApplicScelta(BigDecimal idApplicScelta) {
        this.idApplicScelta = idApplicScelta;
    }

    @Column(name = "NM_AUTOR_AGGIUNTA")
    public String getNmAutorAggiunta() {
        return this.nmAutorAggiunta;
    }

    public void setNmAutorAggiunta(String nmAutorAggiunta) {
        this.nmAutorAggiunta = nmAutorAggiunta;
    }

    private UsrVCheckRuoloDefaultId usrVCheckRuoloDefaultId;

    @EmbeddedId()
    public UsrVCheckRuoloDefaultId getUsrVCheckRuoloDefaultId() {
        return usrVCheckRuoloDefaultId;
    }

    public void setUsrVCheckRuoloDefaultId(UsrVCheckRuoloDefaultId usrVCheckRuoloDefaultId) {
        this.usrVCheckRuoloDefaultId = usrVCheckRuoloDefaultId;
    }
}
