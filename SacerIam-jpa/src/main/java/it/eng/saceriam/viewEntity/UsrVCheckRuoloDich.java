package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHECK_RUOLO_DICH database table.
 */
@Entity
@Table(name = "USR_V_CHECK_RUOLO_DICH")
public class UsrVCheckRuoloDich implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutorAggiunta;

    private BigDecimal idApplicDich;

    private BigDecimal idOrganizIamRuolo;

    private String nmAutorAggiunta;

    public UsrVCheckRuoloDich() {
    }

    @Column(name = "DS_AUTOR_AGGIUNTA")
    public String getDsAutorAggiunta() {
        return this.dsAutorAggiunta;
    }

    public void setDsAutorAggiunta(String dsAutorAggiunta) {
        this.dsAutorAggiunta = dsAutorAggiunta;
    }

    @Column(name = "ID_APPLIC_DICH")
    public BigDecimal getIdApplicDich() {
        return this.idApplicDich;
    }

    public void setIdApplicDich(BigDecimal idApplicDich) {
        this.idApplicDich = idApplicDich;
    }

    @Column(name = "ID_ORGANIZ_IAM_RUOLO")
    public BigDecimal getIdOrganizIamRuolo() {
        return this.idOrganizIamRuolo;
    }

    public void setIdOrganizIamRuolo(BigDecimal idOrganizIamRuolo) {
        this.idOrganizIamRuolo = idOrganizIamRuolo;
    }

    @Column(name = "NM_AUTOR_AGGIUNTA")
    public String getNmAutorAggiunta() {
        return this.nmAutorAggiunta;
    }

    public void setNmAutorAggiunta(String nmAutorAggiunta) {
        this.nmAutorAggiunta = nmAutorAggiunta;
    }

    private UsrVCheckRuoloDichId usrVCheckRuoloDichId;

    @EmbeddedId()
    public UsrVCheckRuoloDichId getUsrVCheckRuoloDichId() {
        return usrVCheckRuoloDichId;
    }

    public void setUsrVCheckRuoloDichId(UsrVCheckRuoloDichId usrVCheckRuoloDichId) {
        this.usrVCheckRuoloDichId = usrVCheckRuoloDichId;
    }
}
