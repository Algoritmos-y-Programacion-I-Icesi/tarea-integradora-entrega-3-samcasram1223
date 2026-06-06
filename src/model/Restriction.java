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

    /**
     * Retorna el primer participante de la restriccion.
     *
     * @return primer participante involucrado en la restriccion.
     */
    public Participant getParticipant1() {
        return this.participant1;
    }

    /**
     * Establece el primer participante de la restriccion.
     *
     * @param participant1 primer participante involucrado en la restriccion.
     */
    public void setParticipant1(Participant participant1) {
        this.participant1 = participant1;
    }

    /**
     * Retorna el segundo participante de la restriccion.
     *
     * @return segundo participante involucrado en la restriccion.
     */
    public Participant getParticipant2() {
        return this.participant2;
    }

    /**
     * Establece el segundo participante de la restriccion.
     *
     * @param participant2 segundo participante involucrado en la restriccion.
     */
    public void setParticipant2(Participant participant2) {
        this.participant2 = participant2;
    }
}
