package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Poziv.
 */
@Entity
@Table(name = "poziv")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poziv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "potvrdjeno")
    private Boolean potvrdjeno;

    @ManyToOne
    private Gost gost;

    @ManyToOne
    private Rezervacija rezervacija;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPotvrdjeno() {
        return potvrdjeno;
    }

    public Poziv potvrdjeno(Boolean potvrdjeno) {
        this.potvrdjeno = potvrdjeno;
        return this;
    }

    public void setPotvrdjeno(Boolean potvrdjeno) {
        this.potvrdjeno = potvrdjeno;
    }

    public Gost getGost() {
        return gost;
    }

    public Poziv gost(Gost gost) {
        this.gost = gost;
        return this;
    }

    public void setGost(Gost gost) {
        this.gost = gost;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public Poziv rezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
        return this;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
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
        Poziv poziv = (Poziv) o;
        if (poziv.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poziv.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Poziv{" +
            "id=" + getId() +
            ", potvrdjeno='" + isPotvrdjeno() + "'" +
            "}";
    }
}
