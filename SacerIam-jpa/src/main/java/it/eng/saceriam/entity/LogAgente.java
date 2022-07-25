package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the LOG_AGENTE database table.
 *
 */
@Entity
@Table(name = "LOG_AGENTE")
@NamedQuery(name = "LogAgente.findAll", query = "SELECT l FROM LogAgente l")
public class LogAgente implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAgente;
    private String nmAgente;
    private String tipoAgentePremis;
    private String tipoOrigineAgente;

    public LogAgente() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SLOG_AGENTE")
    @Column(name = "ID_AGENTE")
    public Long getIdAgente() {
        return this.idAgente;
    }

    public void setIdAgente(Long idAgente) {
        this.idAgente = idAgente;
    }

    @Column(name = "NM_AGENTE")
    public String getNmAgente() {
        return this.nmAgente;
    }

    public void setNmAgente(String nmAgente) {
        this.nmAgente = nmAgente;
    }

    @Column(name = "TIPO_AGENTE_PREMIS")
    public String getTipoAgentePremis() {
        return this.tipoAgentePremis;
    }

    public void setTipoAgentePremis(String tipoAgentePremis) {
        this.tipoAgentePremis = tipoAgentePremis;
    }

    @Column(name = "TIPO_ORIGINE_AGENTE")
    public String getTipoOrigineAgente() {
        return this.tipoOrigineAgente;
    }

    public void setTipoOrigineAgente(String tipoOrigineAgente) {
        this.tipoOrigineAgente = tipoOrigineAgente;
    }

}
