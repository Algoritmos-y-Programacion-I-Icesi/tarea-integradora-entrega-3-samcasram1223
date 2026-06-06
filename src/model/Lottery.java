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

    /**
     * Retorna el nombre del sorteo.
     *
     * @return nombre del sorteo.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Establece el nombre del sorteo.
     *
     * @param name nombre del sorteo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna la descripcion del sorteo.
     *
     * @return descripcion del sorteo.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Establece la descripcion del sorteo.
     *
     * @param description descripcion del sorteo.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retorna el presupuesto sugerido del sorteo.
     *
     * @return presupuesto del sorteo.
     */
    public double getBudget() {
        return this.budget;
    }

    /**
     * Establece el presupuesto sugerido del sorteo.
     *
     * @param budget presupuesto del sorteo.
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Retorna la fecha programada para el sorteo.
     *
     * @return fecha del sorteo.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Establece la fecha programada para el sorteo.
     *
     * @param date fecha del sorteo.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Retorna el estado actual del sorteo.
     *
     * @return estado del sorteo.
     */
    public LotteryStatusEnum getStatus() {
        return this.status;
    }

    /**
     * Establece el estado actual del sorteo.
     *
     * @param status estado del sorteo.
     */
    public void setStatus(LotteryStatusEnum status) {
        this.status = status;
    }

    /**
     * Retorna la lista de participantes del sorteo.
     *
     * @return lista de participantes.
     */
    public ArrayList<Participant> getParticipants() {
        return this.participants;
    }

    /**
     * Establece la lista de participantes del sorteo.
     *
     * @param participants lista de participantes.
     */
    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    /**
     * Retorna la lista de restricciones del sorteo.
     *
     * @return lista de restricciones.
     */
    public ArrayList<Restriction> getRestrictions() {
        return this.restrictions;
    }

    /**
     * Establece la lista de restricciones del sorteo.
     *
     * @param restrictions lista de restricciones.
     */
    public void setRestrictions(ArrayList<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * Retorna el identificador numerico del sorteo.
     *
     * @return identificador del sorteo.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Establece el identificador numerico del sorteo.
     *
     * @param id identificador del sorteo.
     */
    public void setId(int id) {
        this.id = id;
    }
}
