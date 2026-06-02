package model;
/**
 * Clase que representa una restricción entre dos participantes en el sorteo de amigo secreto.
 */
public class Restriction {
    private  Participant participant1;
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
