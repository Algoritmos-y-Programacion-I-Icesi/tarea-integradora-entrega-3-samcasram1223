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

    /**
     * Retorna el tipo del accesorio.
     *
     * @return tipo del accesorio.
     */
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo del accesorio.
     *
     * @param type tipo del accesorio.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retorna la marca del accesorio.
     *
     * @return marca del accesorio.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Establece la marca del accesorio.
     *
     * @param brand marca del accesorio.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Retorna el material del accesorio.
     *
     * @return material del accesorio.
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Establece el material del accesorio.
     *
     * @param material material del accesorio.
     */
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
