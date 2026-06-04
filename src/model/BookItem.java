package model;

/**
 * Clase que representa un libro en la lista de deseos.
 * Extiende WishListItem con atributos especificos de libros.
 *
 * 
 */
public class BookItem extends WishListItem {

    private static final long serialVersionUID = 1L;

    private String author;
    private String publisher;
    private String genre;

    /**
     * Constructor de un elemento de tipo libro.
     *
     * @param name      nombre del libro.
     * @param author    autor del libro.
     * @param publisher editorial del libro.
     * @param genre     genero literario.
     * pre: name y author no pueden ser nulos.
     * post: se crea un elemento libro con los atributos indicados.
     */
    public BookItem(String name, String author, String publisher, String genre) {
        super(name);
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Retorna los detalles del libro.
     *
     * @return cadena con nombre, autor, editorial y genero.
     */
    @Override
    public String getDetails() {
        return "[Libro] Nombre: " + getName() + " | Autor: " + author
                + " | Editorial: " + publisher + " | Genero: " + genre;
    }
}
