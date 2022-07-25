package it.eng.saceriam.amministrazioneEntiConvenzionati.dto;

import it.eng.spagoLite.db.base.row.BaseRow;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author gilioli_p
 */
public class ServizioFatturatoBean extends BaseRow {

    private BigDecimal id_fattura_ente;
    private BigDecimal id_servizio_erogato;
    private String nm_servizio_erogato;
    private BigDecimal aa_servizio_fattura;
    private String tipo_fatturazione;
    private String nm_tariffa;
    private BigDecimal im_valore_tariffa;
    private String cd_tipo_servizio;
    private String nm_sistema_versante;
    private String fl_pagamento;
    private Date dt_erog;
    private BigDecimal qt_scaglione_servizio_fattura;
    private BigDecimal im_servizio_fattura;
    private String tipo_tariffa;

    public ServizioFatturatoBean(long id_fattura_ente, long id_servizio_erogato, String nm_servizio_erogato,
            BigDecimal aa_servizio_fattura, String tipo_fatturazione, String nm_tariffa, BigDecimal im_valore_tariffa,
            String cd_tipo_servizio, String nm_sistema_versante, String fl_pagamento, Date dt_erog,
            BigDecimal qt_scaglione_servizio_fattura, BigDecimal im_servizio_fattura, String tipo_tariffa) {
        this.id_fattura_ente = new BigDecimal(id_fattura_ente);
        this.id_servizio_erogato = new BigDecimal(id_servizio_erogato);
        this.nm_servizio_erogato = nm_servizio_erogato;
        this.aa_servizio_fattura = aa_servizio_fattura;
        this.tipo_fatturazione = tipo_fatturazione;
        this.nm_tariffa = nm_tariffa;
        this.im_valore_tariffa = im_valore_tariffa;
        this.cd_tipo_servizio = cd_tipo_servizio;
        this.nm_sistema_versante = nm_sistema_versante;
        this.fl_pagamento = fl_pagamento;
        this.dt_erog = dt_erog;
        this.qt_scaglione_servizio_fattura = qt_scaglione_servizio_fattura;
        this.im_servizio_fattura = im_servizio_fattura;
        this.tipo_tariffa = tipo_tariffa;
    }

    public String getNm_servizio_erogato() {
        return nm_servizio_erogato;
    }

    public void setNm_servizio_erogato(String nm_servizio_erogato) {
        this.nm_servizio_erogato = nm_servizio_erogato;
    }

    public BigDecimal getAa_servizio_fattura() {
        return aa_servizio_fattura;
    }

    public void setAa_servizio_fattura(BigDecimal aa_servizio_fattura) {
        this.aa_servizio_fattura = aa_servizio_fattura;
    }

    public String getTipo_fatturazione() {
        return tipo_fatturazione;
    }

    public void setTipo_fatturazione(String tipo_fatturazione) {
        this.tipo_fatturazione = tipo_fatturazione;
    }

    public String getNm_tariffa() {
        return nm_tariffa;
    }

    public void setNm_tariffa(String nm_tariffa) {
        this.nm_tariffa = nm_tariffa;
    }

    public BigDecimal getIm_valore_tariffa() {
        return im_valore_tariffa;
    }

    public void setIm_valore_tariffa(BigDecimal im_valore_tariffa) {
        this.im_valore_tariffa = im_valore_tariffa;
    }

    public BigDecimal getId_fattura_ente() {
        return id_fattura_ente;
    }

    public void setId_fattura_ente(BigDecimal id_fattura_ente) {
        this.id_fattura_ente = id_fattura_ente;
    }

    public BigDecimal getId_servizio_erogato() {
        return id_servizio_erogato;
    }

    public void setId_servizio_erogato(BigDecimal id_servizio_erogato) {
        this.id_servizio_erogato = id_servizio_erogato;
    }

    public String getCd_tipo_servizio() {
        return cd_tipo_servizio;
    }

    public void setCd_tipo_servizio(String cd_tipo_servizio) {
        this.cd_tipo_servizio = cd_tipo_servizio;
    }

    public String getNm_sistema_versante() {
        return nm_sistema_versante;
    }

    public void setNm_sistema_versante(String nm_sistema_versante) {
        this.nm_sistema_versante = nm_sistema_versante;
    }

    public String getFl_pagamento() {
        return fl_pagamento;
    }

    public void setFl_pagamento(String fl_pagamento) {
        this.fl_pagamento = fl_pagamento;
    }

    public Date getDt_erog() {
        return dt_erog;
    }

    public void setDt_erog(Date dt_erog) {
        this.dt_erog = dt_erog;
    }

    public BigDecimal getQt_scaglione_servizio_fattura() {
        return qt_scaglione_servizio_fattura;
    }

    public void setQt_scaglione_servizio_fattura(BigDecimal qt_scaglione_servizio_fattura) {
        this.qt_scaglione_servizio_fattura = qt_scaglione_servizio_fattura;
    }

    public BigDecimal getIm_servizio_fattura() {
        return im_servizio_fattura;
    }

    public void setIm_servizio_fattura(BigDecimal im_servizio_fattura) {
        this.im_servizio_fattura = im_servizio_fattura;
    }

    public String getTipo_tariffa() {
        return tipo_tariffa;
    }

    public void setTipo_tariffa(String tipo_tariffa) {
        this.tipo_tariffa = tipo_tariffa;
    }

}
