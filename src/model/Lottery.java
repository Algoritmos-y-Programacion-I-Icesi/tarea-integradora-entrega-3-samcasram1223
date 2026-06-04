package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa un sorteo de amigo secreto. Contiene la informacion
 * del sorteo (nombre, descripcion, presupuesto, fecha, estado), los participantes
 * y las restricciones de asignacion. Es serializable para permitir guardar y
 * retomar el estado del programa.
 *
 * 
 */
public class Lottery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private double budget;
    private LocalDate date;
    private int id;
    private LotteryStatusEnum status;
    private ArrayList<Participant> participants;
    private ArrayList<Restriction> restrictions;

    /**
     * Constructor por defecto. Inicializa las listas de participantes y restricciones.
     * post: se crea un sorteo con listas vacias y sin estado definido.
     */
    public Lottery() {
        participants = new ArrayList<>();
        restrictions = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LotteryStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(LotteryStatusEnum status) {
        this.status = status;
    }

    public ArrayList<Participant> getParticipants() {
        return this.participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public ArrayList<Restriction> getRestrictions() {
        return this.restrictions;
    }

    public void setRestrictions(ArrayList<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
