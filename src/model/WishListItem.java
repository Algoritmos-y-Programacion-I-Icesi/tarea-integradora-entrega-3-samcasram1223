package model;

import java.io.Serializable;

/**
 * Clase abstracta que representa un elemento en la lista de deseos de un participante.
 * Define la estructura base para todos los tipos de elementos que pueden ser incluidos
 * en una lista de deseos. Esta clase es extensible para soportar nuevos tipos en el futuro.
 *
 * 
 */
public abstract class WishListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    /**
     * Constructor de un elemento de lista de deseos.
     *
     * @param name nombre del elemento.
     * pre: name no puede ser nulo.
     * post: se crea un elemento con el nombre indicado.
     */
    public WishListItem(String name) {
        this.name = name;
    }

    /**
     * Retorna el nombre del elemento.
     *
     * @return nombre del elemento.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del elemento.
     *
     * @param name nuevo nombre.
     * pre: name no puede ser nulo.
     * post: el nombre del elemento queda actualizado.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna una representación textual del elemento con sus atributos específicos.
     * Cada subclase debe implementar este método para describir sus propios datos.
     *
     * @return cadena con la información del elemento.
     */
    public abstract String getDetails();
}
