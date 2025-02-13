package org.example;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ProgramObrazovanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ProgramObrazovanjaId;

    @Column(nullable = false)
    private String Naziv;

    @Column(nullable = false)
    private long CSVET;

    @OneToMany(mappedBy = "programObrazovanja", cascade = CascadeType.ALL)
    private Set<Upis> upisi;

    public ProgramObrazovanja() {
    }

    public long getProgramObrazovanjaId() {
        return ProgramObrazovanjaId;
    }

    public void setProgramObrazovanjaId(long programObrazovanjaId) {
        ProgramObrazovanjaId = programObrazovanjaId;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public long getCSVET() {
        return CSVET;
    }

    public void setCSVET(long CSVET) {
        this.CSVET = CSVET;
    }

    public Set<Upis> getUpisi() {
        return upisi;
    }

    public void setUpisi(Upis upis) {
        this.upisi.add(upis);
    }
}
