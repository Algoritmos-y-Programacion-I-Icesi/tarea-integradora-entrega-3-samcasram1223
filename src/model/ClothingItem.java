package model;

/**
 * Clase que representa una prenda de vestir en la lista de deseos.
 * Extiende WishListItem con atributos especificos de ropa.
 *
 * 
 */
public class ClothingItem extends WishListItem {

    private static final long serialVersionUID = 1L;

    private String category;
    private String brand;
    private String size;

    /**
     * Constructor de un elemento de ropa.
     *
     * @param name     nombre de la prenda.
     * @param category categoria (camiseta, pantalon, chaqueta, etc.).
     * @param brand    marca de la prenda.
     * @param size     talla de la prenda.
     * pre: name y category no pueden ser nulos.
     * post: se crea un elemento de ropa con los atributos indicados.
     */
    public ClothingItem(String name, String category, String brand, String size) {
        super(name);
        this.category = category;
        this.brand = brand;
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Retorna los detalles de la prenda de vestir.
     *
     * @return cadena con nombre, categoria, marca y talla.
     */
    @Override
    public String getDetails() {
        return "[Prenda de vestir] Nombre: " + getName() + " | Categoria: " + category
                + " | Marca: " + brand + " | Talla: " + size;
    }
}
