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

    /**
     * Retorna el identificador numerico del participante.
     *
     * @return identificador del participante.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Establece el identificador numerico del participante.
     *
     * @param id identificador del participante.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna el correo electronico del participante.
     *
     * @return correo electronico.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Establece el correo electronico del participante.
     *
     * @param email correo electronico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna el nombre del participante.
     *
     * @return nombre del participante.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Establece el nombre del participante.
     *
     * @param name nombre del participante.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna el amigo secreto asignado al participante.
     *
     * @return participante asignado como amigo secreto.
     */
    public Participant getSecretFriend() {
        return this.secretFriend;
    }

    /**
     * Establece el amigo secreto asignado al participante.
     *
     * @param secretFriend participante asignado como amigo secreto.
     */
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
