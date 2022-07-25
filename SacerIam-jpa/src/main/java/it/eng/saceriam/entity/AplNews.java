package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_NEWS database table.
 * 
 */
@Entity
@Table(name = "APL_NEWS")
public class AplNews implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idNews;
    private String dlTesto;
    private String dsOggetto;
    private Date dtFinPubblic;
    private Date dtIniPubblic;
    private String flPubblicHomepage;
    private String flPubblicLogin;
    private List<AplNewsApplic> aplNewsApplics = new ArrayList<>();

    public AplNews() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_NEWS") // @SequenceGenerator(name = "APL_NEWS_IDNEWS_GENERATOR",
                                                               // sequenceName = "SAPL_NEWS", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_NEWS_IDNEWS_GENERATOR")
    @Column(name = "ID_NEWS")
    public Long getIdNews() {
        return this.idNews;
    }

    public void setIdNews(Long idNews) {
        this.idNews = idNews;
    }

    @Column(name = "DL_TESTO")
    public String getDlTesto() {
        return this.dlTesto;
    }

    public void setDlTesto(String dlTesto) {
        this.dlTesto = dlTesto;
    }

    @Column(name = "DS_OGGETTO")
    public String getDsOggetto() {
        return this.dsOggetto;
    }

    public void setDsOggetto(String dsOggetto) {
        this.dsOggetto = dsOggetto;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_PUBBLIC")
    public Date getDtFinPubblic() {
        return this.dtFinPubblic;
    }

    public void setDtFinPubblic(Date dtFinPubblic) {
        this.dtFinPubblic = dtFinPubblic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_PUBBLIC")
    public Date getDtIniPubblic() {
        return this.dtIniPubblic;
    }

    public void setDtIniPubblic(Date dtIniPubblic) {
        this.dtIniPubblic = dtIniPubblic;
    }

    @Column(name = "FL_PUBBLIC_HOMEPAGE", columnDefinition = "char(1)")
    public String getFlPubblicHomepage() {
        return this.flPubblicHomepage;
    }

    public void setFlPubblicHomepage(String flPubblicHomepage) {
        this.flPubblicHomepage = flPubblicHomepage;
    }

    @Column(name = "FL_PUBBLIC_LOGIN", columnDefinition = "char(1)")
    public String getFlPubblicLogin() {
        return this.flPubblicLogin;
    }

    public void setFlPubblicLogin(String flPubblicLogin) {
        this.flPubblicLogin = flPubblicLogin;
    }

    // bi-directional many-to-one association to AplNewsApplic
    @OneToMany(mappedBy = "aplNew", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public List<AplNewsApplic> getAplNewsApplics() {
        return this.aplNewsApplics;
    }

    public void setAplNewsApplics(List<AplNewsApplic> aplNewsApplics) {
        this.aplNewsApplics = aplNewsApplics;
    }

}