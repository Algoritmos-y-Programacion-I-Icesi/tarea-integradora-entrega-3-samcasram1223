package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un participante en el sorteo de amigo secreto.
 * Cada participante tiene un correo electronico como identificador unico,
 * un nombre, una referencia a su amigo secreto asignado, y una lista de deseos.
 *
 * 
 */
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String name;
    private Participant secretFriend;
    private int id;
    private boolean consultedAssignment;
    private ArrayList<WishListItem> wishList;

    /**
     * Constructor de un participante.
     *
     * @param email correo electronico del participante (identificador unico).
     * @param name  nombre del participante.
     * @param id    identificador numerico del participante dentro del sorteo.
     * pre: email y name no pueden ser nulos.
     * post: se crea un participante con lista de deseos vacia y sin asignacion consultada.
     */
    public Participant(String email, String name, int id) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.consultedAssignment = false;
        this.wishList = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant getSecretFriend() {
        return this.secretFriend;
    }

    public void setSecretFriend(Participant secretFriend) {
        this.secretFriend = secretFriend;
    }

    /**
     * Retorna si el participante ya consulto su asignacion de amigo secreto.
     *
     * @return true si ya consulto, false en caso contrario.
     */
    public boolean isConsultedAssignment() {
        return consultedAssignment;
    }

    /**
     * Establece si el participante ya consulto su asignacion.
     *
     * @param consultedAssignment true si ya consulto.
     * pre: el sorteo debe haber sido ejecutado.
     * post: el estado de consulta del participante queda actualizado.
     */
    public void setConsultedAssignment(boolean consultedAssignment) {
        this.consultedAssignment = consultedAssignment;
    }

    /**
     * Retorna la lista de deseos del participante.
     *
     * @return lista de elementos de deseos.
     */
    public ArrayList<WishListItem> getWishList() {
        return wishList;
    }

    /**
     * Establece la lista de deseos del participante.
     *
     * @param wishList nueva lista de deseos.
     * pre: wishList no puede ser nula.
     * post: la lista de deseos del participante queda reemplazada.
     */
    public void setWishList(ArrayList<WishListItem> wishList) {
        this.wishList = wishList;
    }
}
