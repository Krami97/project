package org.example;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Polaznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PolaznikID;
    @Column(nullable = false)
    private String Ime;
    @Column(nullable = false)
    private String prezime;

    @OneToMany(mappedBy = "polaznik", cascade = CascadeType.ALL)
    private Set<Upis> upisi;

    public Polaznik() {
    }

    public long getPolaznikID() {
        return PolaznikID;
    }

    public void setPolaznikID(long polaznikID) {
        PolaznikID = polaznikID;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public Set<Upis> getUpisi() {
        return upisi;
    }

    public void setUpisi(Upis upis) {
        this.upisi.add(upis);
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
