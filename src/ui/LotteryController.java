package ui;

import exceptions.DuplicateParticipantException;
import exceptions.FileOperationException;
import exceptions.InvalidLotteryStateException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.*;

/**
 * Clase que representa el controlador principal del sistema SorteoSecreto.
 * Gestiona la interaccion con el usuario a traves de un menu en consola,
 * permitiendo registrar sorteos, gestionar participantes y listas de deseos,
 * ejecutar sorteos, exportar informacion y guardar el estado del programa.
 *
 * 
 */
public class LotteryController {

    private SecretLottery system;
    private Scanner scanner;

    /**
     * Constructor del controlador. Inicializa el sistema y carga el estado guardado.
     * post: el sistema queda listo y con la informacion de la sesion anterior (si existe).
     */
    public LotteryController() {
        system = new SecretLottery();
        scanner = new Scanner(System.in);
        try {
            system.loadState();
        } catch (FileOperationException e) {
            System.out.println("Advertencia al cargar el estado: " + e.getMessage());
        }
    }

    /**
     * Metodo principal de entrada del programa.
     *
     * @param args argumentos de linea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        LotteryController lc = new LotteryController();
        lc.menu();
    }

    /**
     * Despliega el menu principal y gestiona la navegacion entre opciones.
     * El menu se muestra repetidamente hasta que el usuario elija salir.
     *
     * pre: el sistema debe estar inicializado.
     * post: el programa atiende las opciones del usuario hasta que se seleccione salir.
     */
    public void menu() {
        int option = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("     SISTEMA DE SORTEO AMIGO SECRETO");
            System.out.println("========================================");
            System.out.println("\nOPCIONES PRINCIPALES:");
            System.out.println("1. Registrar nuevo sorteo");
            System.out.println("2. Modificar sorteo");
            System.out.println("3. Clonar sorteo");
            System.out.println("4. Seleccionar sorteo (habilita opciones de participantes y sorteo)");

            if (system.getSelectLottery() != null) {
                System.out.println("\n--- SORTEO ACTUAL: " + system.getSelectLottery().getName() + " ---");
                System.out.println("\nOPCIONES DE PARTICIPANTES:");
                System.out.println("5. Agregar participante");
                System.out.println("6. Modificar participante");
                System.out.println("7. Eliminar participante");
                System.out.println("8. Importar participantes desde archivo");
                System.out.println("\nOPCIONES DE LISTA DE DESEOS:");
                System.out.println("9.  Agregar elemento a lista de deseos");
                System.out.println("10. Modificar elemento de lista de deseos");
                System.out.println("11. Borrar elemento de lista de deseos");
                System.out.println("12. Exportar lista de deseos del amigo asignado");
                System.out.println("13. Ver asignacion de un participante");
                System.out.println("\nOPCIONES DE SORTEO:");
                System.out.println("14. Agregar restriccion");
                System.out.println("15. Ejecutar sorteo");
                System.out.println("16. Cancelar sorteo");
                System.out.println("17. Consultar estado del sorteo");
                System.out.println("18. Generar reporte final (matricial)");
                System.out.println("19. Volver al menu principal");
            } else {
                System.out.println("\n(Debes seleccionar un sorteo primero para ver mas opciones)");
            }

            System.out.println("\n20. Guardar estado del programa");
            System.out.println("0.  Salir");
            System.out.println("========================================");
            System.out.print("Elige una opcion: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Error: Debes ingresar un numero valido.");
                continue;
            }

            switch (option) {
                case 1: registerLottery(); break;
                case 2: modifyLottery(); break;
                case 3: cloneLottery(); break;
                case 4: selectLottery(); break;
                case 5: addParticipant(); break;
                case 6: modifyParticipant(); break;
                case 7: removeParticipant(); break;
                case 8: importParticipants(); break;
                case 9: addWishListItem(); break;
                case 10: modifyWishListItem(); break;
                case 11: deleteWishListItem(); break;
                case 12: exportSecretFriendWishList(); break;
                case 13: viewAssignment(); break;
                case 14: addRestriction(); break;
                case 15: executeLottery(); break;
                case 16: cancelLottery(); break;
                case 17: checkStatus(); break;
                case 18: printFinalReport(); break;
                case 19: system.setSelectLottery(null); break;
                case 20: saveState(); break;
                case 0: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (option != 0);
    }

    // ========================== SORTEOS ==========================

    /**
     * Solicita al usuario los datos de un nuevo sorteo y lo registra en el sistema.
     * pre: ninguna.
     * post: si los datos son validos, se agrega un nuevo sorteo al sistema.
     */
    public void registerLottery() {
        System.out.println("\n--- REGISTRAR NUEVO SORTEO ---");
        System.out.print("Nombre del sorteo: ");
        String name = scanner.nextLine();
        System.out.print("Descripcion: ");
        String desc = scanner.nextLine();
        System.out.print("Presupuesto: ");
        double budg = scanner.nextDouble();
        System.out.print("Ano: ");
        int year = scanner.nextInt();
        System.out.print("Mes (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Dia: ");
        int day = scanner.nextInt();
        scanner.nextLine();
        try {
            LocalDate evDate = LocalDate.of(year, month, day);
            String status = system.registerLottery(name, desc, budg, evDate);
            if (status != null) {
                System.out.println(status);
            }
        } catch (Exception e) {
            System.out.println("Error: Fecha invalida.");
        }
    }

    /**
     * Permite al usuario seleccionar y modificar un sorteo existente.
     * pre: debe haber al menos un sorteo registrado.
     * post: los datos del sorteo seleccionado quedan actualizados si son validos.
     */
    public void modifyLottery() {
        System.out.println("\n--- MODIFICAR SORTEO ---");
        showLotteries();
        System.out.print("Selecciona el numero del sorteo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id < 1 || id > system.getLotterys().size()) {
            System.out.println("Sorteo no valido.");
            return;
        }
        System.out.print("Nuevo nombre: ");
        String name = scanner.nextLine();
        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine();
        System.out.print("Nuevo presupuesto: ");
        double budg = scanner.nextDouble();
        System.out.print("Ano: ");
        int year = scanner.nextInt();
        System.out.print("Mes (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Dia: ");
        int day = scanner.nextInt();
        scanner.nextLine();
        try {
            LocalDate evDate = LocalDate.of(year, month, day);
            String status = system.modifyLottery(id - 1, name, desc, budg, evDate);
            System.out.println(status + "\n");
        } catch (Exception e) {
            System.out.println("Error: Fecha invalida.");
        }
    }

    /**
     * Permite al usuario clonar un sorteo existente con un nuevo nombre.
     * pre: debe haber al menos un sorteo registrado.
     * post: se agrega al sistema una copia del sorteo seleccionado con el nuevo nombre.
     */
    public void cloneLottery() {
        System.out.println("\n--- CLONAR SORTEO ---");
        showLotteries();
        System.out.print("Selecciona el numero del sorteo a clonar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id < 1 || id > system.getLotterys().size()) {
            System.out.println("Sorteo no valido.");
            return;
        }
        System.out.print("Nuevo nombre para el sorteo clonado: ");
        String name = scanner.nextLine();
        String status = system.cloneLottery(id - 1, name);
        System.out.println(status + "\n");
    }

    /**
     * Permite al usuario seleccionar el sorteo sobre el cual operar.
     * pre: debe haber al menos un sorteo registrado.
     * post: el sorteo seleccionado queda activo en el sistema.
     */
    public void selectLottery() {
        if (system.getLotterys().isEmpty()) {
            System.out.println("\nNo hay sorteos. Crea uno primero.");
            return;
        }
        showLotteries();
        System.out.print("Selecciona el numero del sorteo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id < 1 || id > system.getLotterys().size()) {
            System.out.println("Opcion no valida.");
            return;
        }
        system.setSelectLottery(system.getLotterys().get(id - 1));
        System.out.println("Sorteo '" + system.getSelectLottery().getName() + "' seleccionado.\n");
    }

    /**
     * Muestra la lista de sorteos disponibles en el sistema.
     * pre: ninguna.
     * post: se imprime la lista de sorteos con su numero, nombre y estado.
     */
    private void showLotteries() {
        ArrayList<Lottery> lotteries = system.getLotterys();
        if (lotteries.isEmpty()) {
            System.out.println("No hay sorteos disponibles.");
            return;
        }
        System.out.println("\n--- SORTEOS DISPONIBLES ---");
        for (int i = 0; i < lotteries.size(); i++) {
            Lottery lottery = lotteries.get(i);
            System.out.println("[" + (i + 1) + "] " + lottery.getName() + " - Estado: " + lottery.getStatus());
        }
        System.out.println("--------------------------\n");
    }

    // ========================== PARTICIPANTES ==========================

    /**
     * Solicita al usuario los datos de uno o mas participantes y los agrega al sorteo seleccionado.
     * pre: debe haber un sorteo seleccionado.
     * post: los participantes validos quedan registrados en el sorteo.
     */
    public void addParticipant() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- AGREGAR PARTICIPANTES ---");
        System.out.print("Cuantos participantes deseas agregar? ");
        int numParticipants = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numParticipants; i++) {
            System.out.println("\nParticipante " + (i + 1) + ":");
            System.out.print("  Email: ");
            String email = scanner.nextLine();
            System.out.print("  Nombre: ");
            String name = scanner.nextLine();
            try {
                system.createParticipant(email, name);
            } catch (DuplicateParticipantException e) {
                System.out.println("Error: " + e.getMessage());
                i--;
            }
        }
        System.out.println();
    }

    /**
     * Permite al usuario modificar los datos de un participante existente.
     * pre: debe haber un sorteo seleccionado con al menos un participante.
     * post: los datos del participante seleccionado quedan actualizados.
     */
    public void modifyParticipant() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- MODIFICAR PARTICIPANTE ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id < 1 || id > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        System.out.print("Nuevo email: ");
        String email = scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String name = scanner.nextLine();
        system.updateParticipant(email, name, id - 1);
        System.out.println();
    }

    /**
     * Muestra la lista de participantes del sorteo seleccionado.
     * pre: debe haber un sorteo seleccionado.
     * post: se imprime la lista de participantes con su numero, nombre y correo.
     */
    private void showParticipants() {
        ArrayList<Participant> participants = system.getSelectLottery().getParticipants();
        if (participants.isEmpty()) {
            System.out.println("No hay participantes.");
            return;
        }
        System.out.println("\n--- PARTICIPANTES ---");
        for (int i = 0; i < participants.size(); i++) {
            Participant p = participants.get(i);
            System.out.println("[" + (i + 1) + "] " + p.getName() + " - " + p.getEmail());
        }
        System.out.println("---------------------\n");
    }

    /**
     * Permite al usuario eliminar un participante del sorteo seleccionado.
     * pre: debe haber un sorteo seleccionado con al menos un participante.
     * post: el participante seleccionado queda eliminado del sorteo.
     */
    public void removeParticipant() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- ELIMINAR PARTICIPANTE ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id < 1 || id > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        system.deleteParticipant(id - 1);
        System.out.println();
    }

    /**
     * Solicita la ruta de un archivo e importa los participantes al sorteo seleccionado.
     * pre: debe haber un sorteo seleccionado.
     * post: los participantes del archivo quedan agregados al sorteo si no son duplicados.
     */
    public void importParticipants() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- IMPORTAR PARTICIPANTES DESDE ARCHIVO ---");
        System.out.print("Ingresa la ruta del archivo (ejemplo: participantes.csv): ");
        String path = scanner.nextLine();
        try {
            system.importParticipantsFromFile(path);
        } catch (FileOperationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========================== LISTA DE DESEOS ==========================

    /**
     * Permite al usuario seleccionar un participante y agregar un elemento a su lista de deseos.
     * pre: debe haber un sorteo seleccionado con al menos un participante.
     * post: el elemento queda agregado a la lista de deseos del participante seleccionado.
     */
    public void addWishListItem() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- AGREGAR ELEMENTO A LISTA DE DESEOS ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int pid = scanner.nextInt();
        scanner.nextLine();
        if (pid < 1 || pid > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        WishListItem item = buildWishListItem();
        if (item != null) {
            system.addWishListItem(pid - 1, item);
        }
    }

    /**
     * Permite al usuario modificar un elemento existente en la lista de deseos de un participante.
     * pre: debe haber un sorteo seleccionado, el participante debe tener al menos un elemento.
     * post: el elemento seleccionado queda reemplazado por el nuevo elemento ingresado.
     */
    public void modifyWishListItem() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- MODIFICAR ELEMENTO DE LISTA DE DESEOS ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int pid = scanner.nextInt();
        scanner.nextLine();
        if (pid < 1 || pid > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        Participant participant = system.getSelectLottery().getParticipants().get(pid - 1);
        if (participant.getWishList().isEmpty()) {
            System.out.println("Este participante no tiene elementos en su lista de deseos.");
            return;
        }
        showWishList(participant);
        System.out.print("Selecciona el numero del elemento a modificar: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        if (itemId < 1 || itemId > participant.getWishList().size()) {
            System.out.println("Elemento no valido.");
            return;
        }
        WishListItem newItem = buildWishListItem();
        if (newItem != null) {
            system.updateWishListItem(pid - 1, itemId - 1, newItem);
        }
    }

    /**
     * Permite al usuario eliminar un elemento de la lista de deseos de un participante.
     * pre: debe haber un sorteo seleccionado, el participante debe tener al menos un elemento.
     * post: el elemento seleccionado queda eliminado de la lista de deseos del participante.
     */
    public void deleteWishListItem() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- BORRAR ELEMENTO DE LISTA DE DESEOS ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int pid = scanner.nextInt();
        scanner.nextLine();
        if (pid < 1 || pid > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        Participant participant = system.getSelectLottery().getParticipants().get(pid - 1);
        if (participant.getWishList().isEmpty()) {
            System.out.println("Este participante no tiene elementos en su lista de deseos.");
            return;
        }
        showWishList(participant);
        System.out.print("Selecciona el numero del elemento a borrar: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        if (itemId < 1 || itemId > participant.getWishList().size()) {
            System.out.println("Elemento no valido.");
            return;
        }
        system.deleteWishListItem(pid - 1, itemId - 1);
    }

    /**
     * Muestra la lista de deseos de un participante por consola.
     *
     * @param participant participante del cual mostrar la lista de deseos.
     * pre: participant no puede ser nulo.
     * post: se imprime la lista de deseos con el numero y los detalles de cada elemento.
     */
    private void showWishList(Participant participant) {
        System.out.println("\nLista de deseos de " + participant.getName() + ":");
        if (participant.getWishList().isEmpty()) {
            System.out.println("  (Sin elementos)");
        } else {
            for (int i = 0; i < participant.getWishList().size(); i++) {
                System.out.println("  [" + (i + 1) + "] " + participant.getWishList().get(i).getDetails());
            }
        }
        System.out.println();
    }

    /**
     * Solicita al usuario el tipo de elemento de lista de deseos y sus datos, y retorna
     * una instancia del subtipo correspondiente.
     *
     * pre: ninguna.
     * post: retorna un WishListItem del tipo elegido o null si el tipo no es valido.
     * @return WishListItem creado segun la eleccion del usuario, o null si la opcion no es valida.
     */
    private WishListItem buildWishListItem() {
        System.out.println("\nTipo de elemento:");
        System.out.println("1. Tecnologia");
        System.out.println("2. Prenda de vestir");
        System.out.println("3. Libro");
        System.out.println("4. Experiencia/Actividad");
        System.out.println("5. Accesorio");
        System.out.print("Elige el tipo: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre del elemento: ");
        String name = scanner.nextLine();

        switch (type) {
            case 1:
                System.out.print("Marca: ");
                String brand = scanner.nextLine();
                System.out.print("Modelo/Referencia: ");
                String model = scanner.nextLine();
                System.out.print("Especificaciones (color, tamaño, etc.): ");
                String specs = scanner.nextLine();
                return new TechItem(name, brand, model, specs);
            case 2:
                System.out.print("Categoria (camiseta, pantalon, chaqueta, etc.): ");
                String category = scanner.nextLine();
                System.out.print("Marca: ");
                String clothBrand = scanner.nextLine();
                System.out.print("Talla: ");
                String size = scanner.nextLine();
                return new ClothingItem(name, category, clothBrand, size);
            case 3:
                System.out.print("Autor: ");
                String author = scanner.nextLine();
                System.out.print("Editorial: ");
                String publisher = scanner.nextLine();
                System.out.print("Genero: ");
                String genre = scanner.nextLine();
                return new BookItem(name, author, publisher, genre);
            case 4:
                System.out.print("Tipo de experiencia (cine, cena, curso, etc.): ");
                String expType = scanner.nextLine();
                System.out.print("Ciudad o ubicacion: ");
                String location = scanner.nextLine();
                System.out.print("Restricciones (horarios, preferencias, etc.): ");
                String restrictions = scanner.nextLine();
                return new ExperienceItem(name, expType, location, restrictions);
            case 5:
                System.out.print("Tipo (reloj, bolso, billetera, etc.): ");
                String accType = scanner.nextLine();
                System.out.print("Marca: ");
                String accBrand = scanner.nextLine();
                System.out.print("Material: ");
                String material = scanner.nextLine();
                return new AccessoryItem(name, accType, accBrand, material);
            default:
                System.out.println("Tipo de elemento no valido.");
                return null;
        }
    }

    /**
     * Permite al usuario exportar la lista de deseos del amigo secreto asignado a un archivo .txt.
     * pre: debe haber un sorteo seleccionado en estado DRAWN.
     * post: se genera un archivo .txt en la ruta indicada con la informacion del amigo secreto.
     */
    public void exportSecretFriendWishList() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- EXPORTAR LISTA DE DESEOS DEL AMIGO ASIGNADO ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int pid = scanner.nextInt();
        scanner.nextLine();
        if (pid < 1 || pid > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        System.out.print("Ruta donde deseas exportar el archivo (ejemplo: lista.txt): ");
        String path = scanner.nextLine();
        try {
            system.exportSecretFriendWishList(pid - 1, path);
        } catch (FileOperationException | InvalidLotteryStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Permite simular el acceso de un participante y mostrar su asignacion de amigo secreto.
     * pre: debe haber un sorteo seleccionado en estado DRAWN.
     * post: se imprime la asignacion del participante y su lista de deseos; el participante queda marcado como consultado.
     */
    public void viewAssignment() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- VER ASIGNACION DE PARTICIPANTE ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int pid = scanner.nextInt();
        scanner.nextLine();
        if (pid < 1 || pid > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido.");
            return;
        }
        try {
            system.viewAssignment(pid - 1);
        } catch (InvalidLotteryStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========================== SORTEO ==========================

    /**
     * Solicita al usuario confirmacion y agrega una restriccion entre dos participantes.
     * pre: debe haber un sorteo seleccionado con al menos dos participantes.
     * post: la restriccion queda registrada en el sorteo seleccionado.
     */
    public void addRestriction() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\n--- AGREGAR RESTRICCION ---");
        System.out.println("(Una restriccion evita que dos personas sean amigos secretos)\n");
        showParticipants();
        System.out.print("Primer participante (numero): ");
        int id1 = scanner.nextInt();
        System.out.print("Segundo participante (numero): ");
        int id2 = scanner.nextInt();
        scanner.nextLine();
        int size = system.getSelectLottery().getParticipants().size();
        if (id1 < 1 || id1 > size || id2 < 1 || id2 > size || id1 == id2) {
            System.out.println("Numeros de participantes no validos.");
            return;
        }
        system.addRestriction(id1 - 1, id2 - 1);
        System.out.println("Restriccion agregada exitosamente.\n");
    }

    /**
     * Ejecuta el sorteo de amigo secreto para el sorteo seleccionado.
     * pre: debe haber un sorteo seleccionado en estado CREATED con al menos 2 participantes.
     * post: el sorteo queda ejecutado y cada participante tiene asignado su amigo secreto.
     */
    public void executeLottery() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\nEjecutando sorteo '" + system.getSelectLottery().getName() + "'...");
        try {
            system.executeLottery();
            System.out.println("Sorteo ejecutado.\n");
        } catch (InvalidLotteryStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Anula el sorteo seleccionado cambiando su estado a CANCELLED.
     * pre: debe haber un sorteo seleccionado en estado CREATED.
     * post: el sorteo queda en estado CANCELLED.
     */
    public void cancelLottery() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        System.out.println("\nCancelando sorteo '" + system.getSelectLottery().getName() + "'...");
        try {
            system.cancelLottery();
            System.out.println("Sorteo cancelado.\n");
        } catch (InvalidLotteryStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Muestra el estado general del sorteo seleccionado con el reporte de participantes.
     * pre: debe haber un sorteo seleccionado.
     * post: se imprime la informacion del sorteo y si cada participante consulto su asignacion.
     */
    public void checkStatus() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        system.printLotteryStatus();
    }

    /**
     * Genera y muestra el reporte final en formato matricial del sorteo seleccionado.
     * pre: debe haber un sorteo seleccionado en estado DRAWN y la fecha actual debe ser posterior al sorteo.
     * post: se imprime la matriz de asignaciones y restricciones por consola.
     */
    public void printFinalReport() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero.");
            return;
        }
        try {
            system.printFinalReport();
        } catch (InvalidLotteryStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Guarda el estado actual del sistema en disco para retomarlo en sesiones futuras.
     * pre: ninguna.
     * post: el estado del sistema queda serializado en el archivo de datos.
     */
    public void saveState() {
        try {
            system.saveState();
        } catch (FileOperationException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}
