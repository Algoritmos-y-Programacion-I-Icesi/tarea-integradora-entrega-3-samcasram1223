package model;

import java.io.Serializable;

/**
 * Clase que representa una restriccion entre dos participantes en el sorteo.
 * Una restriccion indica que los dos participantes involucrados no pueden
 * ser asignados como amigos secretos entre si.
 *
 * 
 */
public class Restriction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Participant participant1;
    private Participant participant2;

    public Participant getParticipant1() {
        return this.participant1;
    }

    public void setParticipant1(Participant participant1) {
        this.participant1 = participant1;
    }

    public Participant getParticipant2() {
        return this.participant2;
    }

    public void setParticipant2(Participant participant2) {
        this.participant2 = participant2;
    }
}
