package exceptions;

/**
 * Excepcion lanzada cuando ocurre un error durante operaciones de lectura
 * o escritura de archivos (importacion de participantes, exportacion de lista
 * de deseos, guardado o carga del estado del sistema).
 *
 * 
 */
public class FileOperationException extends Exception {

    /**
     * Constructor con mensaje descriptivo.
     *
     * @param message descripcion del error de archivo.
     */
    public FileOperationException(String message) {
        super(message);
    }
}
