package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que representa el sistema de sorteo de amigo secreto. Esta clase es
 * responsable de gestionar los sorteos, participantes y restricciones, así
 * como de ejecutar el sorteo y cancelar sorteos existentes. La clase utiliza
 * otras clases como Lottery, Participant y Restriction para representar los
 * diferentes elementos del sistema y sus relaciones.
 */
public class SecretLottery {

    private Lottery selectLottery;

    private ArrayList<Lottery> lotterys;

    public SecretLottery() {
        lotterys = new ArrayList<>();
    }


    /**
     * Metodo que permite registrar un nuevo sorteo 
     * @param name: nombre del sorteo
     * @param description: descripcion del sorteo
     * @param budget: presupuesto del sorteo
     * @param evenDate: fecha del sorteo
     * pre: name, description y evenDate no pueden ser nulos, budget debe ser mayor a 0
     * post: se crea un nuevo sorteo con los datos ingresados y se agrega a la lista de sorteos del sistema
     * @return String: mensaje de error en caso de que existan errores en los campos
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

    /**Metodo que permite validar los campos del sorteo
     * @param name: nombre del sorteo
     * @param description: descripcion del sorteo
     * @param budget: presupuesto del sorteo
     * @param evenDate: fecha del sorteo
     * pre: name, description y evenDate no pueden ser nulos, budget debe ser mayor a 0
     * post: se valida que los campos ingresados sean correctos
     * @return String: mensaje de error en caso de que existan errores en los campos
     */
    private String validateFields(String name, String description,
            double budget, LocalDate evenDate) {

        String error = null;
        if (name == null) {
            error = "El nombre del sorteo es obligatorio";
        }
        if (description == null) {
            error += "\nEl nombre del sorteo es obligatorio";
        }
        if (evenDate == null) {
            error += "\nEl nombre del sorteo es obligatorio";
        }
        if (budget <= 0) {
            error += "\nEl presupeusto debe ser mayor a 0";
        }
        return error;
    }

    /**
     *  Metodo que permite modificar un sorteo existente
     * @param id: id del sorteo a modificar
     * @param name: nuevo nombre del sorteo
     * @param description: nueva descripción del sorteo
     * @param budget: nuevo presupuesto del sorteo
     * @param eventDate: nueva fecha del sorteo
     * pre: id debe existir en la lista de sorteos, name, description y eventDate no pueden ser nulos, budget debe ser mayor a 0
     * post: se actualizan los datos del sorteo con los nuevos valores ingresados
     * @return String: mensaje de error en caso de que existan errores en los campos
     */
    public String modifyLottery(int id, String name, String description,
            double budget, LocalDate evenDate) {
        String status = this.validateFields(name, description, budget, evenDate);

        if (status != null) {
            Lottery updatelLottery = this.lotterys.get(id);

            updatelLottery.setName(name);
            updatelLottery.setDescription(description);
            updatelLottery.setBudget(budget);
            updatelLottery.setDate(evenDate);
            System.out.println("Fecha del sorteo: " + evenDate);
            System.out.println("Sorteo creado exitosamente.");

            this.lotterys.set(id, updatelLottery);

            status = "Sorteo actualizado correctamente";

        } else {
            status = "No lo pudimos actualizar debido a los siguientes errores : " + status;
        }
        return status;
    }

    /**
    *  Metodo que permite clonar un sorteo existente
    * @param id: id del sorteo a clonar
    * @param name: nuevo nombre del sorteo clonado
    * pre: id debe existir en la lista de sorteos, name no puede ser nulo
    * post: se crea un nuevo sorteo con los mismos datos del sorteo original pero con el nuevo nombre ingresado
     */
    public String cloneLottery(int id, String name) {

        String status = null;

        if (name == null) {
            status = "El nombre es obligatorio";
            return status;
        }
        Lottery cloned = this.lotterys.get(id);

        Lottery newLottery = new Lottery();
        newLottery.setId(lotterys.size() + 1);

        newLottery.setBudget(cloned.getBudget());
        newLottery.setName(name);
        newLottery.setDescription(cloned.getDescription());
        newLottery.setDate(cloned.getDate());
        newLottery.setParticipants(cloned.getParticipants());

        status = "Sorteo clonado exitosamente";

        return status;

    }

    /**
     *Metodo que permite agregar un nuevo participante al sorteo seleccionado
    * @param email: email del nuevo participante
    * @param name: nombre del nuevo participante
    * pre: debe haber un sorteo seleccionado, email no puede ser nulo y debe ser único en la lista de participantes del sorteo, name no puede ser nulo
    * post: se crea un nuevo participante con los datos ingresados y se agrega a la lista de participantes del sorteo seleccionado
    * @return boolean: true si el participante fue creado exitosamente, false si hubo un error (email ya existe o campos nulos)
     */
    public boolean createParticipant(String email, String name) {
        if (this.existParticpant(email)) {
            System.out.println("El participante con el email:" + email + " ya existe");
            return false;
        } else {
            int id = this.getSelectLottery().getParticipants().size();
            Participant participant = new Participant(email, name, id);

            this.getSelectLottery().getParticipants().add(participant);
            System.out.println("El participante con el email:" + email + " fue creado exitosamente");
        }
        return true;
    }

    /**
     *
     * Verifica si un participante ya existe en la lista de participantes.
     *
     * @param email El correo electrónico del participante a verificar. pre:
     * email no puede ser nulo y debe ser único en la lista de participantes del
     * sorteo post: se verifica si el participante con el email ingresado ya
     * existe en la lista de participantes del sorteo seleccionado, lo que
     * permite evitar la creación de participantes duplicados y mantener la
     * integridad de los datos del sorteo
     * @return true si el participante existe, false en caso contrario.
     *
     */
    public boolean existParticpant(String email) {

        for (Participant participant : this.selectLottery.getParticipants()) {
            if (participant != null && participant.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    /**
         * Actualiza los datos de un participante existente.
         * @param email El nuevo correo electrónico del participante.
         * @param name El nuevo nombre del participante.
         * @param id El ID del participante a actualizar.
         * pre: debe haber un sorteo seleccionado, id debe existir en la lista de participantes del sorteo, email no puede ser nulo y debe ser único en la lista de participantes del sorteo (excepto el participante que se está modificando), name no puede ser nulo
         * post: se actualizan los datos del participante 
         * @return true si el participante fue actualizado exitosamente, false si hubo un error.
     */
    public boolean updateParticipant(String email, String name, int id) {
        Participant updateParticipant = this.getSelectLottery().getParticipants().get(id);

        updateParticipant.setEmail(email);
        updateParticipant.setName(name);

        if (existParticpant(email)) {
            System.out.println("Ya existe un particpante con ese email no se puede modificar");

            return false;
        }
        selectLottery.getParticipants().set(id, updateParticipant);
        return true;
    }

    /**Elimina un participante existente.
         @param id El ID del participante a eliminar.
        * pre: debe haber un sorteo seleccionado, id debe existir en la lista de participantes del sorteo
        * post: se elimina el participante de la lista de participantes del sorteo seleccionado, lo que permite mantener actualizada la lista de participantes y reflejar los cambios realizados por el usuario
        * @return true si el participante fue eliminado exitosamente, false si hubo un error.
     */
    public boolean deleteParticipant(int id) {

        selectLottery.getParticipants().remove(id);
        System.out.println("Participante eliminado exitosamente");
        return true;
    }

 

    public void addRestriction(int id1, int id2) {
        Restriction restriction = new Restriction();
        restriction.setParticipant1(this.getSelectLottery().getParticipants().get(id1));
        restriction.setParticipant2(this.getSelectLottery().getParticipants().get(id2));
        selectLottery.getRestrictions().add(restriction);

    }

    /**
     * Ejecuta el sorteo del amigo secreto. pre: debe haber un sorteo
     * seleccionado, el sorteo debe tener al menos 2 participantes, no debe
     * haber restricciones que impidan realizar el sorteo post: se asigna un
     * amigo secreto a cada participante del sorteo seleccionado, cumpliendo con
     * las condiciones de no repetición y restricciones establecidas, lo que
     * permite llevar a cabo el sorteo de manera exitosa y generar las
     * asignaciones correspondientes
     *
     * @return true si el sorteo fue exitoso, false si hubo un error.
     */
    public boolean executeLottery() {
        if (getSelectLottery() == null) {
            System.out.println("No hay un sorteo seleccionado para ejecutar.");
            return false;
        }
        if(getSelectLottery().getStatus().equals(LotteryStatusEnum.CANCELLED) || getSelectLottery().getStatus().equals(LotteryStatusEnum.DRAWN)){
            System.out.println("No se puede ejecutar el sorteo en ese estado");
            return false;
        }
        if (getSelectLottery().getParticipants().size() < 2) {
            System.out.println("El sorteo debe tener al menos 2 participantes para poder ejecutarse.");
            return false;
        }

        
        ArrayList<Participant> participants = selectLottery.getParticipants();

        ArrayList<Participant> assignments = new ArrayList<>(participants.size());

        if (participants==null || participants.isEmpty()) {
            System.out.println("No hay participantes para sortear.");
            return false;
        }
        Random rand = new Random();
        boolean valid = false;

        while (!valid) {

            // Se crea el arreglo asignaciones con los mismps valores de los participantes 
            for (int i = 0; i < participants.size(); i++) {
                assignments.add(participants.get(i));

            }
            // Se mezcla el arreglo asignaciones para cumplir con la condicion de no repetir asignado 
            for (int i = 0; i < participants.size(); i++) {
                int random = rand.nextInt(participants.size());
                Participant tmp = assignments.get(i);
                assignments.set(i, assignments.get(random));
                assignments.set(random, tmp);
            }
            // Se descarta para no sacarse a si mismo y que no existan restricciones entre el asignado y el asignador, si se encuentra alguna de estas condiciones se vuelve a mezclar el arreglo asignaciones hasta que se cumplan las condiciones para todos los participantes
            valid = true;
            for (int i = 0; i < participants.size(); i++) {

                if (participants.get(i).getEmail().equals(assignments.get(i).getEmail())) {
                    valid = false;
                    break;
                }
                if (selectLottery.getRestrictions() != null && !selectLottery.getRestrictions().isEmpty()) {
                    for (Restriction restriction : selectLottery.getRestrictions()) {
                    if ((restriction.getParticipant1().getEmail().equals(participants.get(i).getEmail()) && restriction.getParticipant2().getEmail().equals(assignments.get(i).getEmail()))
                            || (restriction.getParticipant2().getEmail().equals(participants.get(i).getEmail()) && restriction.getParticipant1().getEmail().equals(assignments.get(i).getEmail()))) {
                        valid = false;
                        System.out.println("Se encontró una restricción que impide realizar el sorteo, se volverá a mezclar las asignaciones.");
                        break;
                    }
                }
                }
            }
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
     * Metodo que permite cancelar un sorteo existente pre: debe haber un sorteo
     * seleccionado, el sorteo no debe estar en estado CANCELLED o DRAWN post:
     * se cambia el estado del sorteo seleccionado a CANCELLED, lo que permite
     * reflejar la cancelación del sorteo y evitar que se realicen acciones
     * adicionales sobre el mismo
     *
     * @return boolean: true si el sorteo fue cancelado exitosamente, false si
     * hubo un error (sorteo ya está cancelado o sorteado)
     */
    public boolean cancelLottery() {
        if (getSelectLottery().getStatus().equals(LotteryStatusEnum.CANCELLED) || getSelectLottery().getStatus().equals(LotteryStatusEnum.DRAWN)) {
            System.out.println("No se puede cancelar el sorteo en ese estado");
            return false;

        }
        getSelectLottery().setStatus(LotteryStatusEnum.CANCELLED);
        return true;

    }

    public ArrayList<Lottery> getLotterys() {
        return this.lotterys;
    }

    public void setLotterys(ArrayList<Lottery> lotterys) {
        this.lotterys = lotterys;
    }

    public Lottery getSelectLottery() {
        return this.selectLottery;
    }

    public void setSelectLottery(Lottery selectLottery) {
        this.selectLottery = selectLottery;
    }

}
