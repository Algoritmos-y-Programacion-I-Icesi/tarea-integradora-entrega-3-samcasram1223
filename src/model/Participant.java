package model;
/**
 * Clase que representa un participante en el sorteo de amigo secreto.
 */
public class Participant {
    
    private String email;
    private String name;
    private Participant secretFriend;
    private int id;

    public Participant(String email,String name , int id){
        this.email=email;
        this.name=name;
        this.id=id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant getSecretFriend() {
        return this.secretFriend;
    }

    public void setSecretFriend(Participant secretFriend) {
        this.secretFriend = secretFriend;
    }

}
    
