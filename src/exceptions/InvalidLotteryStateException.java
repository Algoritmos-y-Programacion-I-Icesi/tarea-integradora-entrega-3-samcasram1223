package exceptions;

/**
 * Excepcion lanzada cuando se intenta realizar una operacion sobre un sorteo
 * cuyo estado no lo permite (por ejemplo, ejecutar un sorteo ya sorteado o anulado,
 * o consultar el reporte final antes de la fecha del evento).
 *
 * 
 */
public class InvalidLotteryStateException extends Exception {

    /**
     * Constructor con mensaje descriptivo.
     *
     * @param message descripcion del estado invalido.
     */
    public InvalidLotteryStateException(String message) {
        super(message);
    }
}
