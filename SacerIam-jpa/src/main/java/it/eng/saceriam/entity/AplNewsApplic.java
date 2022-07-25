package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the APL_NEWS_APPLIC database table.
 *
 */
@Entity
@Table(name = "APL_NEWS_APPLIC")
public class AplNewsApplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idNewsApplic;
    private AplApplic aplApplic;
    private AplNews aplNew;

    public AplNewsApplic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_NEWS_APPLIC") // @SequenceGenerator(name =
                                                                      // "APL_NEWS_APPLIC_IDNEWSAPPLIC_GENERATOR",
                                                                      // sequenceName = "SAPL_NEWS_APPLIC",
                                                                      // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_NEWS_APPLIC_IDNEWSAPPLIC_GENERATOR")
    @Column(name = "ID_NEWS_APPLIC")
    public Long getIdNewsApplic() {
        return this.idNewsApplic;
    }

    public void setIdNewsApplic(Long idNewsApplic) {
        this.idNewsApplic = idNewsApplic;
    }

    // bi-directional many-to-one association to AplApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")
    public AplApplic getAplApplic() {
        return this.aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
        this.aplApplic = aplApplic;
    }

    // bi-directional many-to-one association to AplNews
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ID_NEWS", nullable = false)
    public AplNews getAplNew() {
        return this.aplNew;
    }

    public void setAplNew(AplNews aplNew) {
        this.aplNew = aplNew;
    }
}