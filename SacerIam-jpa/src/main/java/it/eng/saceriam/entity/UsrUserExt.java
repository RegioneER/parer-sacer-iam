package it.eng.saceriam.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_USER_EXT database table.
 *
 */
@Entity
@Table(name = "USR_USER_EXT")
public class UsrUserExt implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUserIam;
    private String dlCertClient;

    public UsrUserExt() {
        // Costruttore vuoto
    }

    @Id
    @Column(name = "ID_USER_IAM")
    public Long getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(Long idUserIam) {
        this.idUserIam = idUserIam;
    }

    @Lob
    @Column(name = "DL_CERT_CLIENT")
    public String getDlCertClient() {
        return dlCertClient;
    }

    public void setDlCertClient(String dlCertClient) {
        this.dlCertClient = dlCertClient;
    }

}
