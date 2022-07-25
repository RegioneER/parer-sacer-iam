package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class PrfVLisAutorRuoloId implements Serializable {

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

    private String tiDichAutor;

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
        return tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
        this.tiDichAutor = tiDichAutor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.idApplic);
        hash = 61 * hash + Objects.hashCode(this.idAutor);
        hash = 61 * hash + Objects.hashCode(this.idRuolo);
        hash = 61 * hash + Objects.hashCode(this.tiDichAutor);
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
        final PrfVLisAutorRuoloId other = (PrfVLisAutorRuoloId) obj;
        if (!Objects.equals(this.tiDichAutor, other.tiDichAutor)) {
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
        return true;
    }

}
