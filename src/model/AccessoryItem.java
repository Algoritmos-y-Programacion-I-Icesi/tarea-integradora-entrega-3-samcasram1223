package model;

/**
 * Clase que representa un accesorio en la lista de deseos.
 * Extiende WishListItem con atributos especificos de accesorios.
 *
 * 
 */
public class AccessoryItem extends WishListItem {

    private static final long serialVersionUID = 1L;

    private String type;
    private String brand;
    private String material;

    /**
     * Constructor de un elemento de tipo accesorio.
     *
     * @param name     nombre del accesorio.
     * @param type     tipo de accesorio (reloj, bolso, billetera, etc.).
     * @param brand    marca del accesorio.
     * @param material material del accesorio.
     * pre: name y type no pueden ser nulos.
     * post: se crea un elemento accesorio con los atributos indicados.
     */
    public AccessoryItem(String name, String type, String brand, String material) {
        super(name);
        this.type = type;
        this.brand = brand;
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Retorna los detalles del accesorio.
     *
     * @return cadena con nombre, tipo, marca y material.
     */
    @Override
    public String getDetails() {
        return "[Accesorio] Nombre: " + getName() + " | Tipo: " + type
                + " | Marca: " + brand + " | Material: " + material;
    }
}
