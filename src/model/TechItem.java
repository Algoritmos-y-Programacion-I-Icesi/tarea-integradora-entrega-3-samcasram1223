package model;

/**
 * Clase que representa un elemento de tecnologia en la lista de deseos.
 * Extiende WishListItem con atributos especificos de productos tecnologicos.
 *
 * 
 */
public class TechItem extends WishListItem {

    private static final long serialVersionUID = 1L;

    private String brand;
    private String model;
    private String specifications;

    /**
     * Constructor de un elemento de tecnologia.
     *
     * @param name           nombre del producto.
     * @param brand          marca del producto.
     * @param model          modelo o referencia del producto.
     * @param specifications especificaciones adicionales (color, tamaño, etc.).
     * pre: name y brand no pueden ser nulos.
     * post: se crea un elemento de tecnologia con los atributos indicados.
     */
    public TechItem(String name, String brand, String model, String specifications) {
        super(name);
        this.brand = brand;
        this.model = model;
        this.specifications = specifications;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    /**
     * Retorna los detalles del elemento de tecnologia.
     *
     * @return cadena con nombre, marca, modelo y especificaciones.
     */
    @Override
    public String getDetails() {
        return "[Tecnologia] Nombre: " + getName() + " | Marca: " + brand
                + " | Modelo: " + model + " | Especificaciones: " + specifications;
    }
}
