package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Prijatelji.
 */
@Entity
@Table(name = "prijatelji")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prijatelji implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_gosta_1")
    private Long idGosta1;

    @Column(name = "id_gosta_2")
    private Long idGosta2;

    @Column(name = "postalan_zahtev")
    private LocalDate postalanZahtev;

    @Column(name = "prihvacen_zahtev")
    private LocalDate prihvacenZahtev;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdGosta1() {
        return idGosta1;
    }

    public Prijatelji idGosta1(Long idGosta1) {
        this.idGosta1 = idGosta1;
        return this;
    }

    public void setIdGosta1(Long idGosta1) {
        this.idGosta1 = idGosta1;
    }

    public Long getIdGosta2() {
        return idGosta2;
    }

    public Prijatelji idGosta2(Long idGosta2) {
        this.idGosta2 = idGosta2;
        return this;
    }

    public void setIdGosta2(Long idGosta2) {
        this.idGosta2 = idGosta2;
    }

    public LocalDate getPostalanZahtev() {
        return postalanZahtev;
    }

    public Prijatelji postalanZahtev(LocalDate postalanZahtev) {
        this.postalanZahtev = postalanZahtev;
        return this;
    }

    public void setPostalanZahtev(LocalDate postalanZahtev) {
        this.postalanZahtev = postalanZahtev;
    }

    public LocalDate getPrihvacenZahtev() {
        return prihvacenZahtev;
    }

    public Prijatelji prihvacenZahtev(LocalDate prihvacenZahtev) {
        this.prihvacenZahtev = prihvacenZahtev;
        return this;
    }

    public void setPrihvacenZahtev(LocalDate prihvacenZahtev) {
        this.prihvacenZahtev = prihvacenZahtev;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prijatelji prijatelji = (Prijatelji) o;
        if (prijatelji.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prijatelji.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prijatelji{" +
            "id=" + getId() +
            ", idGosta1='" + getIdGosta1() + "'" +
            ", idGosta2='" + getIdGosta2() + "'" +
            ", postalanZahtev='" + getPostalanZahtev() + "'" +
            ", prihvacenZahtev='" + getPrihvacenZahtev() + "'" +
            "}";
    }
}
