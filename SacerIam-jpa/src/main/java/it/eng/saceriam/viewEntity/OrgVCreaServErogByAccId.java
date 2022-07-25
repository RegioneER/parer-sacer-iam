package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;

@Embeddable()
public class OrgVCreaServErogByAccId implements Serializable {

    private BigDecimal idAccordoEnte;

    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    private String nmServizioErogato;

    @Column(name = "NM_SERVIZIO_EROGATO")
    public String getNmServizioErogato() {
        return nmServizioErogato;
    }

    public void setNmServizioErogato(String nmServizioErogato) {
        this.nmServizioErogato = nmServizioErogato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrgVCreaServErogByAccId that = (OrgVCreaServErogByAccId) o;
        return idAccordoEnte.equals(that.idAccordoEnte) && nmServizioErogato.equals(that.nmServizioErogato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccordoEnte, nmServizioErogato);
    }
}
