package org.example;

import jakarta.persistence.*;

@Entity
public class Upis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long UpisId;



    @ManyToOne
    @JoinColumn(name = "IDPolaznik", nullable = false)
    private Polaznik polaznik;

    @ManyToOne
    @JoinColumn(name = "IDProgramObrazovanja", nullable = false)
    private ProgramObrazovanja programObrazovanja;

    public Upis() {
    }

    public long getUpisId() {
        return UpisId;
    }

    public void setUpisId(long upisId) {
        UpisId = upisId;
    }

    public Polaznik getPolaznik() {
        return polaznik;
    }

    public void setPolaznik(Polaznik polaznik) {
        this.polaznik = polaznik;
    }

    public ProgramObrazovanja getProgramObrazovanja() {
        return programObrazovanja;
    }

    public void setProgramObrazovanja(ProgramObrazovanja programObrazovanja) {
        this.programObrazovanja = programObrazovanja;
    }
}
