package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;

@Embeddable()
public class UsrVAbilEnteSupportoToAddId implements Serializable {

    private BigDecimal idEnteConvenz;

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    private BigDecimal idUserIamCor;

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
        return idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
        this.idUserIamCor = idUserIamCor;
    }

    private BigDecimal idUserIamGestito;

    @Column(name = "ID_USER_IAM_GESTITO")
    public BigDecimal getIdUserIamGestito() {
        return idUserIamGestito;
    }

    public void setIdUserIamGestito(BigDecimal idUserIamGestito) {
        this.idUserIamGestito = idUserIamGestito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsrVAbilEnteSupportoToAddId that = (UsrVAbilEnteSupportoToAddId) o;
        return idEnteConvenz.equals(that.idEnteConvenz) && idUserIamCor.equals(that.idUserIamCor)
                && idUserIamGestito.equals(that.idUserIamGestito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnteConvenz, idUserIamCor, idUserIamGestito);
    }
}
