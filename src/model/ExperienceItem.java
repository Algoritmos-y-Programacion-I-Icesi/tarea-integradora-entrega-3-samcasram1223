package model;

/**
 * Clase que representa una experiencia o actividad en la lista de deseos.
 * Extiende WishListItem con atributos especificos de experiencias.
 *
 * 
 */
public class ExperienceItem extends WishListItem {

    private static final long serialVersionUID = 1L;

    private String type;
    private String location;
    private String restrictions;

    /**
     * Constructor de un elemento de tipo experiencia.
     *
     * @param name         nombre de la experiencia.
     * @param type         tipo de experiencia (cine, cena, curso, etc.).
     * @param location     ciudad o ubicacion.
     * @param restrictions restricciones adicionales (horarios, preferencias, etc.).
     * pre: name y type no pueden ser nulos.
     * post: se crea un elemento experiencia con los atributos indicados.
     */
    public ExperienceItem(String name, String type, String location, String restrictions) {
        super(name);
        this.type = type;
        this.location = location;
        this.restrictions = restrictions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * Retorna los detalles de la experiencia.
     *
     * @return cadena con nombre, tipo, ubicacion y restricciones.
     */
    @Override
    public String getDetails() {
        return "[Experiencia] Nombre: " + getName() + " | Tipo: " + type
                + " | Ubicacion: " + location + " | Restricciones: " + restrictions;
    }
}
