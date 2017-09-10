package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ponudjac.
 */
@Entity
@Table(name = "ponudjac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ponudjac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private User userID;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Ponudjac login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Ponudjac password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public Ponudjac ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Ponudjac prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public Ponudjac email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUserID() {
        return userID;
    }

    public Ponudjac userID(User user) {
        this.userID = user;
        return this;
    }

    public void setUserID(User user) {
        this.userID = user;
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
        Ponudjac ponudjac = (Ponudjac) o;
        if (ponudjac.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ponudjac.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ponudjac{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
