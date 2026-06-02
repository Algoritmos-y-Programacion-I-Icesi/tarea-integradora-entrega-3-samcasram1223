package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.Lottery;
import model.Participant;
import model.Restriction;
import model.SecretLottery;

/**
 * Clase que representa el controlador del sistema de sorteo de amigo secreto.
 * Esta clase es responsable de interactuar con el usuario a través de la
 * consola, permitiendo realizar operaciones como registrar un nuevo sorteo,
 * modificar un sorteo existente, clonar un sorteo, seleccionar un sorteo para
 * operar sobre él, agregar participantes a un sorteo, modificar participantes
 * existentes, eliminar participantes, agregar restricciones entre
 * participantes, ejecutar el sorteo y cancelar sorteos existentes. La clase
 * utiliza una instancia de SecretLottery para gestionar los sorteos y sus
 * elementos relacionados.
 * 
 * @author Samuel Castro
 */
public class LotteryController {

    private SecretLottery system;
    private Scanner scanner;

    public LotteryController() {
        system = new SecretLottery();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LotteryController lc = new LotteryController();
        lc.menu();
    }

    public void menu() {

        int option=0;

        do {
            System.out.println("\n========================================");
            System.out.println("     SISTEMA DE SORTEO AMIGO SECRETO");
            System.out.println("========================================");
            System.out.println("\nOPCIONES PRINCIPALES:");
            System.out.println("1. Registrar nuevo sorteo");
            System.out.println("2. Modificar sorteo");
            System.out.println("3. Clonar sorteo");
            System.out.println("4. Seleccionar sorteo(Habilita Opciones Participantes,Opciones sorteo)");
            
            if(system.getSelectLottery() != null){
                System.out.println("\n--- SORTEO ACTUAL: " + system.getSelectLottery().getName() + " ---");
                System.out.println("\nOPCIONES DE PARTICIPANTES:");
                System.out.println("5. Agregar participante");
                System.out.println("6. Modificar participante");
                System.out.println("7. Eliminar participante");
                System.out.println("\nOPCIONES DE SORTEO:");
                System.out.println("8. Agregar restriccion");
                System.out.println("9. Ejecutar sorteo");
                System.out.println("10. Cancelar sorteo");
                System.out.println("11. Ver estado del sorteo");
                System.out.println("12. Volver al menu principal");
            } else {
                System.out.println("\n(Debes seleccionar un sorteo primero)");
            }
           
            System.out.println("\n0. Salir");
            System.out.println("========================================");
            System.out.print("Elige una opcion: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Error: Debes ingresar un numero valido");
                continue;
            }

            switch (option) {
                case 1:
                    registerLottery();
                    break;
                case 2:
                    modifyLottery();
                    break;
                case 3:
                    cloneLottery();
                    break;
                case 4:
                    selectLottery();
                    break;
                case 5:
                    addParticipant();
                    break;
                case 6:
                    modifyParticipant();
                    break;
                case 7:
                    removeParticipant();
                    break;
                case 8:
                    addRestriction();
                    break;
                case 9:
                    executeLottery();
                    break;
                case 10:
                    cancelLottery();
                    break;
                case 11:
                    checkStatus();
                    break;
                case 12:
                    system.setSelectLottery(null);
                    break;
            }

        } while (option != 0);
    }

    //* Metodo que permite registrar un nuevo sorteo 
    // @param name: nombre del sorteo
    // @param description: descripcion del sorteo
    // @param budget: presupuesto del sorteo
    // @param evenDate: fecha del sorteo
    // pre: name, description y evenDate no pueden ser nulos, budget debe ser mayor a 0
    // post: se crea un nuevo sorteo con los datos ingresados y se agrega a la lista de sorteos del sistema
    // @return String: mensaje de error en caso de que existan errores en los campos
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
            System.out.println("Error: Fecha invalida");
        }
    }

    //* Metodo que permite modificar un sorteo existente
    /* @param id: id del sorteo a modificar
     * @param name: nuevo nombre del sorteo
     * @param description: nueva descripción del sorteo
     * @param budget: nuevo presupuesto del sorteo
     * @param eventDate: nueva fecha del sorteo
     * pre: id debe existir en la lista de sorteos, name, description y eventDate no pueden ser nulos, budget debe ser mayor a 0
     * post: se actualizan los datos del sorteo con los nuevos valores ingresados
     * @return String: mensaje de error en caso de que existan errores en los campos
     */
    public void modifyLottery() {
        System.out.println("\n--- MODIFICAR SORTEO ---");
        showLotteries();
        System.out.print("Selecciona el numero del sorteo: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id < 1 || id > system.getLotterys().size()) {
            System.out.println("Sorteo no valido");
            return;
        }
        
        System.out.print("Nuevo nombre: ");
        String name = scanner.nextLine();
        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine();
        System.out.print("Nuevo presupuesto: ");
        double budg = scanner.nextDouble();
        scanner.nextLine();
        
        String status = system.modifyLottery(id - 1, name, desc, budg, null);
        System.out.println(status + "\n");
    }

    //* Metodo que permite clonar un sorteo existente
    /* @param id: id del sorteo a clonar
     * @param name: nuevo nombre del sorteo clonado
     * pre: id debe existir en la lista de sorteos, name no puede ser nulo
     * post: se crea un nuevo sorteo con los mismos datos del sorteo original pero con el nuevo nombre ingresado, y se vuelve a agrega a la lista de sorteos del sistema
     * @return String: mensaje de error en caso de que existan errores en los campos
     */
    public void cloneLottery() {
        System.out.println("\n--- CLONAR SORTEO ---");
        showLotteries();
        System.out.print("Selecciona el numero del sorteo a clonar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id < 1 || id > system.getLotterys().size()) {
            System.out.println("Sorteo no valido");
            return;
        }
        
        System.out.print("Nuevo nombre para el sorteo clonado: ");
        String name = scanner.nextLine();
        String status = system.cloneLottery(id - 1, name);
        System.out.println(status + "\n");
    }

    /**
     * Metodo que permite seleccionar el sorteo sobre el cual se va a operar
     * pre: id del sorteo debe existir en la lista de sorteos post: se establece
     * el sorteo seleccionado como el sorteo activo en el sistema, lo que
     * permite realizar operaciones como agregar participantes, agregar
     * restricciones, ejecutar el sorteo, etc. sobre ese sorteo específico
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
            System.out.println("Opcion no valida");
            return;
        }
        system.setSelectLottery(system.getLotterys().get(id - 1));
        System.out.println("Sorteo '" + system.getSelectLottery().getName() + "' seleccionado.\n");
    }

    /**
     * Metodo que permite mostrar los sorteos creados pre: no requiere
     * precondiciones post: se muestra la lista de sorteos creados con su id y
     * nombre, lo que permite al usuario identificar y seleccionar el sorteo
     * sobre el cual desea usar
     */
    private void showLotteries() {
        ArrayList<Lottery> lotteries = system.getLotterys();
        if (lotteries.isEmpty()) {
            System.out.println("No hay sorteos disponibles");
            return;
        }
        System.out.println("\n--- SORTEOS DISPONIBLES ---");
        for (int i = 0; i < lotteries.size(); i++) {
            Lottery lottery = lotteries.get(i);
            System.out.println("[" + (i + 1) + "] " + lottery.getName() + " - Estado: " + lottery.getStatus());
        }
        System.out.println("--------------------------\n");
    }

    /**
     * Metodo que permite agregar un nuevo participante al sorteo seleccionado
     * pre: debe haber un sorteo seleccionado, email no puede ser nulo y debe ser único en la lista de participantes del sorteo, name no puede ser nulo
     * post: se crea un nuevo participante con los datos ingresados y se agrega a la lista de participantes del sorteo seleccionado
     * @return boolean: true si el participante fue creado exitosamente, false si hubo un error (email ya existe o campos nulos)
     */
    public void addParticipant() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
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

            boolean created = system.createParticipant(email, name);
            if (!created) {
                i--;
            }
        }
        System.out.println();
    }

    // Metodo que permite modificar un participante existente en el sorteo seleccionado
    /* @param email: nuevo email del participante a modificar
     * @param name: nuevo nombre del participante a modificar
     * @param id: id del participante a modificar
     * pre: debe haber un sorteo seleccionado, id debe existir en la lista de participantes del sorteo, email no puede ser nulo y debe ser único en la lista de participantes del sorteo (excepto el participante que se está modificando), name no puede ser nulo
     * post: se actualizan los datos del participante con los nuevos valores ingresados
     * @return boolean: true si el participante fue modificado exitosamente, false si hubo un error (email ya existe o campos nulos)
     */
    public void modifyParticipant() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
            return;
        }
        
        System.out.println("\n--- MODIFICAR PARTICIPANTE ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        if (id < 1 || id > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido");
            return;
        }
        
        System.out.print("Nuevo email: ");
        String email = scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String name = scanner.nextLine();

        system.updateParticipant(email, name, id);
        System.out.println();
    }

    private void showParticipants() {
        ArrayList<Participant> participants = system.getSelectLottery().getParticipants();
        if (participants.isEmpty()) {
            System.out.println("No hay participantes");
            return;
        }
        System.out.println("\n--- PARTICIPANTES ---");
        for (Participant p : participants) {
            System.out.println("[" + p.getId() + "] " + p.getName() + " - " + p.getEmail());
        }
        System.out.println("---------------------\n");
    }

    public void removeParticipant() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
            return;
        }
        
        System.out.println("\n--- ELIMINAR PARTICIPANTE ---");
        showParticipants();
        System.out.print("Selecciona el numero del participante a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        if (id < 1 || id > system.getSelectLottery().getParticipants().size()) {
            System.out.println("Participante no valido");
            return;
        }
        
        system.deleteParticipant(id);
        System.out.println();
    }

    public void addRestriction() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
            return;
        }
        
        System.out.println("\n--- AGREGAR RESTRICCION ---");
        System.out.println("(Una restriccion evita que dos personas sean amigos secretos)\n");
        showParticipants();
        System.out.print("Primer participante: ");
        int id1 = scanner.nextInt();
        System.out.print("Segundo participante: ");
        int id2 = scanner.nextInt();
        scanner.nextLine();

        if (id1 < 1 || id1 > system.getSelectLottery().getParticipants().size() ||
            id2 < 1 || id2 > system.getSelectLottery().getParticipants().size()) {
            System.out.println("IDs no validos");
            return;
        }
        
        system.addRestriction(id1, id2);
        System.out.println();
    }

    public void executeLottery() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
            return;
        }
        
        System.out.println("\nEjecutando sorteo '" + system.getSelectLottery().getName() + "'...");
        system.executeLottery();
        System.out.println("Sorteo ejecutado.\n");
    }

    public void cancelLottery() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
            return;
        }
        
        System.out.println("\nCancelando sorteo '" + system.getSelectLottery().getName() + "'...");
        system.cancelLottery();
        System.out.println("Sorteo cancelado.\n");
    }
    /**
     * Metodo que permite mostrar el estado actual del sorteo seleccionado, incluyendo su nombre, descripcion, presupuesto, fecha, estado y las asignaciones de amigos secretos realizadas hasta el momento
      * pre: debe haber un sorteo seleccionado
      * post: se muestra la información detallada del sorteo seleccionado y las asignaciones de amigos secretos realizadas hasta el momento. Si no hay participantes o asignaciones, se indica que no hay participantes o que no se han realizado asignaciones.
     */
    public void checkStatus() {
        if (system.getSelectLottery() == null) {
            System.out.println("\nDebes seleccionar un sorteo primero");
            return;
        }
        
        Lottery lottery = system.getSelectLottery();
        System.out.println("\n--- ESTADO DEL SORTEO ---");
        System.out.println("Nombre: " + lottery.getName());
        System.out.println("Descripcion: " + lottery.getDescription());
        System.out.println("Presupuesto: " + lottery.getBudget());
        System.out.println("Fecha: " + lottery.getDate());
        System.out.println("Estado: " + lottery.getStatus());
        System.out.println("Restrecciones:");
        if (lottery.getRestrictions().isEmpty()) {
            System.out.println("No hay restricciones");
        } else {
            for (Restriction r : lottery.getRestrictions()) {
                System.out.println("  " + r.getParticipant1().getName() + " <-> " + r.getParticipant2().getName());
            }
        }
        System.out.println("\nAsignaciones:");
        
        if (lottery.getParticipants().isEmpty()) {
            System.out.println("No hay participantes");
        } else {
            for (Participant p : lottery.getParticipants()) {
                String secretFriend = p.getSecretFriend() != null ? p.getSecretFriend().getName() : "No asignado";
                System.out.println("  " + p.getName() + " -> " + secretFriend);
            }
        }
        System.out.println();
    }
}
