package model;

import exceptions.DuplicateParticipantException;
import exceptions.FileOperationException;
import exceptions.InvalidLotteryStateException;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase principal del sistema de sorteo de amigo secreto. Gestiona los sorteos,
 * participantes, restricciones, listas de deseos, persistencia del estado y
 * generacion de reportes. Implementa Serializable para permitir guardar y
 * recuperar el estado del programa entre sesiones.
 *
 * 
 */
public class SecretLottery implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "secretlottery.dat";

    private Lottery selectLottery;
    private ArrayList<Lottery> lotterys;

    /**
     * Constructor del sistema. Inicializa la lista de sorteos.
     * post: el sistema queda listo para registrar sorteos.
     */
    public SecretLottery() {
        lotterys = new ArrayList<>();
    }

    // ========================== SORTEOS ==========================

    /**
     * Registra un nuevo sorteo en el sistema.
     *
     * @param name        nombre del sorteo.
     * @param description descripcion del sorteo.
     * @param budget      presupuesto sugerido por regalo.
     * @param evenDate    fecha de realizacion del sorteo.
     * pre: name, description y evenDate no pueden ser nulos, budget debe ser mayor a 0.
     * post: se crea y agrega un nuevo sorteo con estado CREATED.
     * @return mensaje de error si los campos son invalidos, null si fue exitoso.
     */
    public String registerLottery(String name, String description,
            double budget, LocalDate evenDate) {
        String error = this.validateFields(name, description, budget, evenDate);
        if (error == null) {
            Lottery lottery = new Lottery();
            lottery.setId(lotterys.size() + 1);
            lottery.setName(name);
            lottery.setDescription(description);
            lottery.setBudget(budget);
            lottery.setDate(evenDate);
            lottery.setStatus(LotteryStatusEnum.CREATED);
            System.out.println("Fecha del sorteo: " + evenDate);
            System.out.println("Sorteo creado exitosamente.");
            this.lotterys.add(lottery);
        } else {
            System.out.println("Existen Errores en el ingreso.:");
        }
        return error;
    }

    /**
     * Valida los campos requeridos para crear o modificar un sorteo.
     *
     * @param name        nombre del sorteo.
     * @param description descripcion del sorteo.
     * @param budget      presupuesto sugerido.
     * @param evenDate    fecha del sorteo.
     * pre: los parametros son los datos ingresados por el usuario.
     * post: retorna null si todo es valido, o un mensaje de error acumulado.
     * @return null si los campos son validos, cadena con errores en caso contrario.
     */
    private String validateFields(String name, String description,
            double budget, LocalDate evenDate) {
        String error = null;
        if (name == null || name.trim().isEmpty()) {
            error = "El nombre del sorteo es obligatorio";
        }
        if (description == null || description.trim().isEmpty()) {
            error = (error == null ? "" : error + "\n") + "La descripcion del sorteo es obligatoria";
        }
        if (evenDate == null) {
            error = (error == null ? "" : error + "\n") + "La fecha del sorteo es obligatoria";
        }
        if (budget <= 0) {
            error = (error == null ? "" : error + "\n") + "El presupuesto debe ser mayor a 0";
        }
        return error;
    }

    /**
     * Modifica la informacion de un sorteo existente.
     *
     * @param id          indice del sorteo en la lista.
     * @param name        nuevo nombre.
     * @param description nueva descripcion.
     * @param budget      nuevo presupuesto.
     * @param evenDate    nueva fecha.
     * pre: id debe ser un indice valido, name y description no pueden ser nulos.
     * post: los datos del sorteo quedan actualizados si los campos son validos.
     * @return mensaje con el resultado de la operacion.
     */
    public String modifyLottery(int id, String name, String description,
            double budget, LocalDate evenDate) {
        String status = this.validateFields(name, description, budget, evenDate);
        if (status == null) {
            Lottery updateLottery = this.lotterys.get(id);
            updateLottery.setName(name);
            updateLottery.setDescription(description);
            updateLottery.setBudget(budget);
            updateLottery.setDate(evenDate);
            System.out.println("Fecha del sorteo: " + evenDate);
            System.out.println("Sorteo actualizado exitosamente.");
            this.lotterys.set(id, updateLottery);
            status = "Sorteo actualizado correctamente";
        } else {
            status = "No lo pudimos actualizar debido a los siguientes errores: " + status;
        }
        return status;
    }

    /**
     * Clona un sorteo existente con un nuevo nombre, copiando participantes
     * y configuracion general pero sin las asignaciones.
     *
     * @param id   indice del sorteo a clonar.
     * @param name nuevo nombre para el sorteo clonado.
     * pre: id debe ser un indice valido en la lista de sorteos, name no puede ser nulo.
     * post: se agrega un nuevo sorteo al sistema con estado CREATED.
     * @return mensaje con el resultado de la operacion.
     */
    public String cloneLottery(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        Lottery cloned = this.lotterys.get(id);
        Lottery newLottery = new Lottery();
        newLottery.setId(lotterys.size() + 1);
        newLottery.setBudget(cloned.getBudget());
        newLottery.setName(name);
        newLottery.setDescription(cloned.getDescription());
        newLottery.setDate(cloned.getDate());
        newLottery.setStatus(LotteryStatusEnum.CREATED);
        // Se copian los participantes sin asignaciones
        for (Participant p : cloned.getParticipants()) {
            Participant copy = new Participant(p.getEmail(), p.getName(), p.getId());
            newLottery.getParticipants().add(copy);
        }
        lotterys.add(newLottery);
        return "Sorteo clonado exitosamente";
    }

    // ========================== PARTICIPANTES ==========================

    /**
     * Crea y agrega un nuevo participante al sorteo seleccionado.
     *
     * @param email correo electronico del participante (identificador unico).
     * @param name  nombre del participante.
     * pre: debe haber un sorteo seleccionado, email debe ser unico en el sorteo.
     * post: se agrega el participante al sorteo si el email no existe aun.
     * @return true si fue creado exitosamente, false si el email ya existe.
     * @throws DuplicateParticipantException si ya existe un participante con ese email.
     */
    public boolean createParticipant(String email, String name) throws DuplicateParticipantException {
        if (this.existParticipant(email)) {
            throw new DuplicateParticipantException("El participante con el email: " + email + " ya existe en este sorteo.");
        }
        int id = this.getSelectLottery().getParticipants().size();
        Participant participant = new Participant(email, name, id);
        this.getSelectLottery().getParticipants().add(participant);
        System.out.println("El participante con el email: " + email + " fue creado exitosamente.");
        return true;
    }

    /**
     * Verifica si un participante ya existe en el sorteo seleccionado por su email.
     *
     * @param email correo electronico a verificar.
     * pre: debe haber un sorteo seleccionado.
     * post: retorna true si el email pertenece a un participante registrado.
     * @return true si el participante existe, false en caso contrario.
     */
    public boolean existParticipant(String email) {
        for (Participant participant : this.selectLottery.getParticipants()) {
            if (participant != null && participant.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza los datos de un participante existente en el sorteo seleccionado.
     *
     * @param email nuevo correo electronico.
     * @param name  nuevo nombre.
     * @param id    indice del participante en la lista.
     * pre: debe haber un sorteo seleccionado, id debe ser un indice valido.
     * post: los datos del participante quedan actualizados si el email no esta duplicado.
     * @return true si fue actualizado exitosamente, false si el email ya pertenece a otro.
     */
    public boolean updateParticipant(String email, String name, int id) {
        Participant updateParticipant = this.getSelectLottery().getParticipants().get(id);
        // Verificar que el nuevo email no pertenezca a otro participante
        for (int i = 0; i < selectLottery.getParticipants().size(); i++) {
            if (i != id && selectLottery.getParticipants().get(i).getEmail().equalsIgnoreCase(email)) {
                System.out.println("Ya existe un participante con ese email, no se puede modificar.");
                return false;
            }
        }
        updateParticipant.setEmail(email);
        updateParticipant.setName(name);
        selectLottery.getParticipants().set(id, updateParticipant);
        return true;
    }

    /**
     * Elimina un participante del sorteo seleccionado por su indice.
     *
     * @param id indice del participante a eliminar.
     * pre: debe haber un sorteo seleccionado, id debe ser un indice valido.
     * post: el participante queda eliminado de la lista del sorteo.
     * @return true siempre que el indice sea valido.
     */
    public boolean deleteParticipant(int id) {
        selectLottery.getParticipants().remove(id);
        System.out.println("Participante eliminado exitosamente.");
        return true;
    }

    /**
     * Importa participantes al sorteo seleccionado desde un archivo CSV o de texto.
     * El archivo debe tener las columnas: email,nombre (separadas por coma).
     * Se omiten duplicados y se reportan los errores encontrados.
     *
     * @param filePath ruta del archivo a leer.
     * pre: debe haber un sorteo seleccionado, el archivo debe existir y ser legible.
     * post: los participantes validos del archivo son agregados al sorteo seleccionado.
     * @throws FileOperationException si el archivo no se puede leer o tiene formato invalido.
     */
    public void importParticipantsFromFile(String filePath) throws FileOperationException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileOperationException("El archivo no existe en la ruta indicada: " + filePath);
        }
        int added = 0;
        int skipped = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                // Se salta el encabezado si contiene la palabra "email"
                if (firstLine && line.toLowerCase().contains("email")) {
                    firstLine = false;
                    continue;
                }
                firstLine = false;
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Linea ignorada (formato invalido): " + line);
                    skipped++;
                    continue;
                }
                String email = parts[0].trim();
                String name = parts[1].trim();
                if (email.isEmpty() || name.isEmpty()) {
                    System.out.println("Linea ignorada (datos vacios): " + line);
                    skipped++;
                    continue;
                }
                if (existParticipant(email)) {
                    System.out.println("Participante duplicado omitido: " + email);
                    skipped++;
                } else {
                    int id = selectLottery.getParticipants().size();
                    selectLottery.getParticipants().add(new Participant(email, name, id));
                    added++;
                }
            }
        } catch (IOException e) {
            throw new FileOperationException("Error al leer el archivo: " + e.getMessage());
        }
        System.out.println("Importacion finalizada. Agregados: " + added + " | Omitidos: " + skipped);
    }

    // ========================== RESTRICCIONES ==========================

    /**
     * Agrega una restriccion de asignacion entre dos participantes del sorteo seleccionado.
     *
     * @param id1 indice del primer participante.
     * @param id2 indice del segundo participante.
     * pre: debe haber un sorteo seleccionado, id1 e id2 deben ser indices validos y distintos.
     * post: se agrega la restriccion al sorteo, impidiendo que esos dos participantes se saquen.
     */
    public void addRestriction(int id1, int id2) {
        Restriction restriction = new Restriction();
        restriction.setParticipant1(this.getSelectLottery().getParticipants().get(id1));
        restriction.setParticipant2(this.getSelectLottery().getParticipants().get(id2));
        selectLottery.getRestrictions().add(restriction);
    }

    // ========================== SORTEO ==========================

    /**
     * Ejecuta el sorteo del amigo secreto para el sorteo seleccionado.
     * Asigna a cada participante exactamente un amigo secreto respetando
     * restricciones y garantizando que nadie se saque a si mismo.
     *
     * pre: debe haber un sorteo seleccionado con al menos 2 participantes en estado CREATED.
     * post: cada participante queda con su amigo secreto asignado y el estado pasa a DRAWN.
     * @return true si el sorteo se ejecuto exitosamente, false si hubo algun problema.
     * @throws InvalidLotteryStateException si el sorteo no esta en estado CREATED.
     */
    public boolean executeLottery() throws InvalidLotteryStateException {
        if (getSelectLottery() == null) {
            System.out.println("No hay un sorteo seleccionado para ejecutar.");
            return false;
        }
        if (!getSelectLottery().getStatus().equals(LotteryStatusEnum.CREATED)) {
            throw new InvalidLotteryStateException("No se puede ejecutar el sorteo en estado: "
                    + getSelectLottery().getStatus());
        }
        if (getSelectLottery().getParticipants().size() < 2) {
            System.out.println("El sorteo debe tener al menos 2 participantes para poder ejecutarse.");
            return false;
        }

        ArrayList<Participant> participants = selectLottery.getParticipants();
        ArrayList<Participant> assignments = new ArrayList<>();
        Random rand = new Random();
        boolean valid = false;
        int maxAttempts = 1000;
        int attempts = 0;

        while (!valid && attempts < maxAttempts) {
            attempts++;
            assignments.clear();
            for (int i = 0; i < participants.size(); i++) {
                assignments.add(participants.get(i));
            }
            // Mezcla aleatoria (Fisher-Yates)
            for (int i = participants.size() - 1; i > 0; i--) {
                int random = rand.nextInt(i + 1);
                Participant tmp = assignments.get(i);
                assignments.set(i, assignments.get(random));
                assignments.set(random, tmp);
            }
            valid = true;
            for (int i = 0; i < participants.size(); i++) {
                // Nadie se saca a si mismo
                if (participants.get(i).getEmail().equals(assignments.get(i).getEmail())) {
                    valid = false;
                    break;
                }
                // Se respetan las restricciones
                if (selectLottery.getRestrictions() != null && !selectLottery.getRestrictions().isEmpty()) {
                    for (Restriction restriction : selectLottery.getRestrictions()) {
                        if ((restriction.getParticipant1().getEmail().equals(participants.get(i).getEmail())
                                && restriction.getParticipant2().getEmail().equals(assignments.get(i).getEmail()))
                                || (restriction.getParticipant2().getEmail().equals(participants.get(i).getEmail())
                                && restriction.getParticipant1().getEmail().equals(assignments.get(i).getEmail()))) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (!valid) break;
            }
        }

        if (!valid) {
            System.out.println("No fue posible encontrar una asignacion valida con las restricciones actuales. El sorteo no fue ejecutado.");
            return false;
        }

        getSelectLottery().setStatus(LotteryStatusEnum.DRAWN);
        System.out.println("--- AMIGO SECRETO ---");
        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setSecretFriend(assignments.get(i));
            System.out.println(participants.get(i).getName() + " -> " + assignments.get(i).getName());
        }
        return true;
    }

    /**
     * Anula el sorteo seleccionado, cambiando su estado a CANCELLED.
     *
     * pre: debe haber un sorteo seleccionado en estado CREATED.
     * post: el estado del sorteo pasa a CANCELLED.
     * @return true si fue cancelado exitosamente, false si el estado no lo permite.
     * @throws InvalidLotteryStateException si el sorteo ya esta en estado DRAWN o CANCELLED.
     */
    public boolean cancelLottery() throws InvalidLotteryStateException {
        if (getSelectLottery().getStatus().equals(LotteryStatusEnum.CANCELLED)
                || getSelectLottery().getStatus().equals(LotteryStatusEnum.DRAWN)) {
            throw new InvalidLotteryStateException("No se puede cancelar el sorteo en estado: "
                    + getSelectLottery().getStatus());
        }
        getSelectLottery().setStatus(LotteryStatusEnum.CANCELLED);
        return true;
    }

    // ========================== LISTA DE DESEOS ==========================

    /**
     * Agrega un elemento a la lista de deseos de un participante del sorteo seleccionado.
     *
     * @param participantId indice del participante en la lista del sorteo.
     * @param item          elemento a agregar a la lista de deseos.
     * pre: debe haber un sorteo seleccionado, participantId debe ser un indice valido.
     * post: el elemento queda agregado a la lista de deseos del participante.
     */
    public void addWishListItem(int participantId, WishListItem item) {
        Participant participant = selectLottery.getParticipants().get(participantId);
        participant.getWishList().add(item);
        System.out.println("Elemento agregado a la lista de deseos de " + participant.getName());
    }

    /**
     * Modifica un elemento existente en la lista de deseos de un participante.
     *
     * @param participantId indice del participante en la lista del sorteo.
     * @param itemId        indice del elemento dentro de la lista de deseos.
     * @param newItem       nuevo elemento que reemplazara al existente.
     * pre: participantId e itemId deben ser indices validos.
     * post: el elemento queda reemplazado en la lista de deseos del participante.
     */
    public void updateWishListItem(int participantId, int itemId, WishListItem newItem) {
        Participant participant = selectLottery.getParticipants().get(participantId);
        participant.getWishList().set(itemId, newItem);
        System.out.println("Elemento modificado en la lista de deseos de " + participant.getName());
    }

    /**
     * Elimina un elemento de la lista de deseos de un participante.
     *
     * @param participantId indice del participante en la lista del sorteo.
     * @param itemId        indice del elemento a eliminar en la lista de deseos.
     * pre: participantId e itemId deben ser indices validos.
     * post: el elemento queda eliminado de la lista de deseos del participante.
     */
    public void deleteWishListItem(int participantId, int itemId) {
        Participant participant = selectLottery.getParticipants().get(participantId);
        participant.getWishList().remove(itemId);
        System.out.println("Elemento eliminado de la lista de deseos de " + participant.getName());
    }

    /**
     * Exporta la lista de deseos del amigo secreto asignado a un participante en un archivo .txt.
     *
     * @param participantId indice del participante que realiza la consulta.
     * @param exportPath    ruta donde se guardara el archivo de exportacion.
     * pre: el sorteo debe haber sido ejecutado, el participante debe tener amigo secreto asignado.
     * post: se crea un archivo .txt con la informacion del amigo secreto y su lista de deseos.
     * @throws FileOperationException       si hay un error al escribir el archivo.
     * @throws InvalidLotteryStateException si el sorteo aun no ha sido ejecutado.
     */
    public void exportSecretFriendWishList(int participantId, String exportPath)
            throws FileOperationException, InvalidLotteryStateException {
        if (!selectLottery.getStatus().equals(LotteryStatusEnum.DRAWN)) {
            throw new InvalidLotteryStateException("El sorteo aun no ha sido ejecutado.");
        }
        Participant participant = selectLottery.getParticipants().get(participantId);
        Participant secretFriend = participant.getSecretFriend();
        if (secretFriend == null) {
            throw new InvalidLotteryStateException("El participante no tiene amigo secreto asignado.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(exportPath))) {
            writer.println("=== LISTA DE DESEOS ===");
            writer.println("Amigo secreto: " + secretFriend.getName());
            writer.println("Correo: " + secretFriend.getEmail());
            writer.println("-----------------------");
            if (secretFriend.getWishList().isEmpty()) {
                writer.println("(Sin elementos en la lista de deseos)");
            } else {
                for (int i = 0; i < secretFriend.getWishList().size(); i++) {
                    writer.println((i + 1) + ". " + secretFriend.getWishList().get(i).getDetails());
                }
            }
            writer.println("======================");
        } catch (IOException e) {
            throw new FileOperationException("Error al escribir el archivo: " + e.getMessage());
        }
        System.out.println("Lista de deseos exportada exitosamente en: " + exportPath);
    }

    /**
     * Muestra por consola la asignacion de amigo secreto y su lista de deseos,
     * marcando al participante como que ya consulto su asignacion.
     *
     * @param participantId indice del participante que consulta su asignacion.
     * pre: el sorteo debe haber sido ejecutado, participantId debe ser un indice valido.
     * post: se imprime la asignacion y la lista de deseos; el participante queda marcado como consultado.
     * @throws InvalidLotteryStateException si el sorteo aun no ha sido ejecutado.
     */
    public void viewAssignment(int participantId) throws InvalidLotteryStateException {
        if (!selectLottery.getStatus().equals(LotteryStatusEnum.DRAWN)) {
            throw new InvalidLotteryStateException("El sorteo aun no ha sido ejecutado.");
        }
        Participant participant = selectLottery.getParticipants().get(participantId);
        Participant secretFriend = participant.getSecretFriend();
        participant.setConsultedAssignment(true);
        System.out.println("\n--- ASIGNACION DE " + participant.getName().toUpperCase() + " ---");
        System.out.println("Tu amigo secreto es: " + secretFriend.getName());
        System.out.println("Correo: " + secretFriend.getEmail());
        System.out.println("\nLista de deseos:");
        if (secretFriend.getWishList().isEmpty()) {
            System.out.println("  (Sin elementos en la lista de deseos)");
        } else {
            for (int i = 0; i < secretFriend.getWishList().size(); i++) {
                System.out.println("  " + (i + 1) + ". " + secretFriend.getWishList().get(i).getDetails());
            }
        }
    }

    /**
     * Muestra el estado general del sorteo seleccionado, incluyendo datos del sorteo
     * y reporte de participantes indicando si consultaron o no su asignacion.
     *
     * pre: debe haber un sorteo seleccionado.
     * post: se imprime por consola la informacion del sorteo y el reporte de participantes.
     */
    public void printLotteryStatus() {
        Lottery lottery = selectLottery;
        System.out.println("\nInformacion del sorteo:");
        System.out.println("Nombre: " + lottery.getName());
        System.out.println("Descripcion: " + lottery.getDescription());
        System.out.println("Presupuesto: $" + lottery.getBudget());
        System.out.println("Fecha de realizacion: " + lottery.getDate());
        System.out.println("Estado: " + lottery.getStatus());
        System.out.println("\nReporte de participantes:");
        System.out.printf("%-30s %-25s%n", "Participante", "Consulto Amigo Secreto?");
        System.out.println("-".repeat(55));
        for (Participant p : lottery.getParticipants()) {
            String consulted = p.isConsultedAssignment() ? "Si" : "No";
            System.out.printf("%-30s %-25s%n", p.getName(), consulted);
        }
    }

    /**
     * Genera y muestra el reporte final en formato matricial con las asignaciones
     * y restricciones del sorteo. Solo disponible cuando el sorteo ya fue ejecutado
     * y la fecha actual es posterior a la fecha del sorteo.
     *
     * pre: el sorteo debe estar en estado DRAWN y la fecha actual debe ser posterior a la del sorteo.
     * post: se imprime la matriz de asignaciones y restricciones por consola.
     * @throws InvalidLotteryStateException si el sorteo no esta en estado DRAWN o la fecha no ha pasado.
     */
    public void printFinalReport() throws InvalidLotteryStateException {
        if (!selectLottery.getStatus().equals(LotteryStatusEnum.DRAWN)) {
            throw new InvalidLotteryStateException("El reporte solo esta disponible cuando el sorteo ha sido ejecutado.");
        }
        if (!LocalDate.now().isAfter(selectLottery.getDate())) {
            throw new InvalidLotteryStateException("El reporte solo esta disponible despues de la fecha del sorteo ("
                    + selectLottery.getDate() + ").");
        }
        ArrayList<Participant> participants = selectLottery.getParticipants();
        int n = participants.size();
        int colWidth = 15;

        System.out.println("\n--- REPORTE FINAL: MATRIZ DE ASIGNACIONES ---");
        System.out.printf("%-" + colWidth + "s", "Part. \\ Asig.");
        for (Participant p : participants) {
            String shortName = p.getName().length() > colWidth - 1
                    ? p.getName().substring(0, colWidth - 1)
                    : p.getName();
            System.out.printf("%-" + colWidth + "s", shortName);
        }
        System.out.println();
        System.out.println("-".repeat(colWidth * (n + 1)));

        for (Participant giver : participants) {
            String shortName = giver.getName().length() > colWidth - 1
                    ? giver.getName().substring(0, colWidth - 1)
                    : giver.getName();
            System.out.printf("%-" + colWidth + "s", shortName);
            for (Participant receiver : participants) {
                String cell;
                if (giver.getEmail().equals(receiver.getEmail())) {
                    cell = "-";
                } else if (giver.getSecretFriend() != null
                        && giver.getSecretFriend().getEmail().equals(receiver.getEmail())) {
                    cell = "R"; // Asignado
                } else {
                    // Verificar si hay restriccion
                    boolean restricted = false;
                    for (Restriction r : selectLottery.getRestrictions()) {
                        if ((r.getParticipant1().getEmail().equals(giver.getEmail())
                                && r.getParticipant2().getEmail().equals(receiver.getEmail()))
                                || (r.getParticipant2().getEmail().equals(giver.getEmail())
                                && r.getParticipant1().getEmail().equals(receiver.getEmail()))) {
                            restricted = true;
                            break;
                        }
                    }
                    cell = restricted ? "X" : " ";
                }
                System.out.printf("%-" + colWidth + "s", cell);
            }
            System.out.println();
        }
        System.out.println("\nConvenciones: R = Asignado  |  X = Restriccion  |  - = Mismo participante");
    }

    // ========================== PERSISTENCIA ==========================

    /**
     * Guarda el estado actual del sistema en un archivo serializado.
     *
     * pre: el directorio src/data debe existir o ser creable.
     * post: el estado del sistema queda guardado en el archivo de datos.
     * @throws FileOperationException si ocurre un error durante la escritura del archivo.
     */
    public void saveState() throws FileOperationException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this.lotterys);
            System.out.println("Estado guardado exitosamente.");
        } catch (IOException e) {
            throw new FileOperationException("Error al guardar el estado: " + e.getMessage());
        }
    }

    /**
     * Carga el estado guardado previamente desde el archivo serializado.
     *
     * pre: el archivo de datos debe existir y ser valido.
     * post: la lista de sorteos queda restaurada con los datos de la sesion anterior.
     * @throws FileOperationException si ocurre un error durante la lectura del archivo.
     */
    @SuppressWarnings("unchecked")
    public void loadState() throws FileOperationException {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            System.out.println("No se encontro informacion guardada. Iniciando sesion nueva.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            this.lotterys = (ArrayList<Lottery>) ois.readObject();
            System.out.println("Estado cargado exitosamente. Sorteos encontrados: " + lotterys.size());
        } catch (IOException | ClassNotFoundException e) {
            throw new FileOperationException("Error al cargar el estado: " + e.getMessage());
        }
    }

    // ========================== GETTERS Y SETTERS ==========================

    /**
     * Retorna la lista de sorteos registrados en el sistema.
     *
     * @return lista de sorteos.
     */
    public ArrayList<Lottery> getLotterys() {
        return this.lotterys;
    }

    /**
     * Establece la lista de sorteos registrados en el sistema.
     *
     * @param lotterys lista de sorteos.
     */
    public void setLotterys(ArrayList<Lottery> lotterys) {
        this.lotterys = lotterys;
    }

    /**
     * Retorna el sorteo actualmente seleccionado para operar.
     *
     * @return sorteo seleccionado.
     */
    public Lottery getSelectLottery() {
        return this.selectLottery;
    }

    /**
     * Establece el sorteo actualmente seleccionado para operar.
     *
     * @param selectLottery sorteo seleccionado.
     */
    public void setSelectLottery(Lottery selectLottery) {
        this.selectLottery = selectLottery;
    }
}
