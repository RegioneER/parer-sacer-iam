package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVAllAutorId implements Serializable {

    private BigDecimal idApplic;

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    private BigDecimal idAutor;

    @Column(name = "ID_AUTOR")
    public BigDecimal getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(BigDecimal idAutor) {
        this.idAutor = idAutor;
    }

    private BigDecimal idRuolo;

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

    private BigDecimal idUserIam;

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

    private BigDecimal idUsoRuolo;

    @Column(name = "ID_USO_RUOLO")
    public BigDecimal getIdUsoRuolo() {
        return idUsoRuolo;
    }

    public void setIdUsoRuolo(BigDecimal idUsoRuolo) {
        this.idUsoRuolo = idUsoRuolo;
    }

    private String tiDichAutor;

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
        return tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
        this.tiDichAutor = tiDichAutor;
    }

    private String tiUsoRuo;

    @Column(name = "TI_USO_RUO")
    public String getTiUsoRuo() {
        return tiUsoRuo;
    }

    public void setTiUsoRuo(String tiUsoRuo) {
        this.tiUsoRuo = tiUsoRuo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.idApplic);
        hash = 73 * hash + Objects.hashCode(this.idAutor);
        hash = 73 * hash + Objects.hashCode(this.idRuolo);
        hash = 73 * hash + Objects.hashCode(this.idUserIam);
        hash = 73 * hash + Objects.hashCode(this.idUsoRuolo);
        hash = 73 * hash + Objects.hashCode(this.tiDichAutor);
        hash = 73 * hash + Objects.hashCode(this.tiUsoRuo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsrVAllAutorId other = (UsrVAllAutorId) obj;
        if (!Objects.equals(this.tiDichAutor, other.tiDichAutor)) {
            return false;
        }
        if (!Objects.equals(this.tiUsoRuo, other.tiUsoRuo)) {
            return false;
        }
        if (!Objects.equals(this.idApplic, other.idApplic)) {
            return false;
        }
        if (!Objects.equals(this.idAutor, other.idAutor)) {
            return false;
        }
        if (!Objects.equals(this.idRuolo, other.idRuolo)) {
            return false;
        }
        if (!Objects.equals(this.idUserIam, other.idUserIam)) {
            return false;
        }
        if (!Objects.equals(this.idUsoRuolo, other.idUsoRuolo)) {
            return false;
        }
        return true;
    }

}
