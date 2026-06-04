package model;

import java.io.Serializable;

/**
 * Enumeracion que define los posibles estados de un sorteo de amigo secreto.
 *
 * 
 */
public enum LotteryStatusEnum implements Serializable {
    /** El sorteo ha sido creado pero aun no se ha ejecutado. */
    CREATED,
    /** El sorteo ha sido ejecutado exitosamente. */
    DRAWN,
    /** El sorteo ha sido anulado y no tiene efecto. */
    CANCELLED
}
