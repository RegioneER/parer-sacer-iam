package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_TIPO_ORGANIZ database table.
 * 
 */
@Entity
@Table(name = "APL_TIPO_ORGANIZ")
@NamedQuery(name = "AplTipoOrganiz.findAll", query = "SELECT a FROM AplTipoOrganiz a")
public class AplTipoOrganiz implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idTipoOrganiz;
    private String flLastLivello;
    private String nmTipoOrganiz;
    private AplApplic aplApplic;
    private List<UsrOrganizIam> usrOrganizIams = new ArrayList<>();

    public AplTipoOrganiz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_TIPO_ORGANIZ") // @SequenceGenerator(name =
                                                                       // "APL_TIPO_ORGANIZ_IDTIPOORGANIZ_GENERATOR",
                                                                       // sequenceName = "SAPL_TIPO_ORGANIZ",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_TIPO_ORGANIZ_IDTIPOORGANIZ_GENERATOR")
    @Column(name = "ID_TIPO_ORGANIZ")
    public Long getIdTipoOrganiz() {
        return this.idTipoOrganiz;
    }

    public void setIdTipoOrganiz(Long idTipoOrganiz) {
        this.idTipoOrganiz = idTipoOrganiz;
    }

    @Column(name = "FL_LAST_LIVELLO", columnDefinition = "char(1)")
    public String getFlLastLivello() {
        return this.flLastLivello;
    }

    public void setFlLastLivello(String flLastLivello) {
        this.flLastLivello = flLastLivello;
    }

    @Column(name = "NM_TIPO_ORGANIZ")
    public String getNmTipoOrganiz() {
        return this.nmTipoOrganiz;
    }

    public void setNmTipoOrganiz(String nmTipoOrganiz) {
        this.nmTipoOrganiz = nmTipoOrganiz;
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

    // bi-directional many-to-one association to UsrOrganizIam
    @OneToMany(mappedBy = "aplTipoOrganiz")
    public List<UsrOrganizIam> getUsrOrganizIams() {
        return this.usrOrganizIams;
    }

    public void setUsrOrganizIams(List<UsrOrganizIam> usrOrganizIams) {
        this.usrOrganizIams = usrOrganizIams;
    }

}