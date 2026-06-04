package exceptions;

/**
 * Excepcion lanzada cuando se intenta registrar un participante con un correo
 * electronico que ya existe en el sorteo seleccionado.
 *
 * @author Samuel Castro
 */
public class DuplicateParticipantException extends Exception {

    /**
     * Constructor con mensaje descriptivo.
     *
     * @param message descripcion del error.
     */
    public DuplicateParticipantException(String message) {
        super(message);
    }
}
