package bank.system.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "client"
        ,schema = "PUBLIC"
)
public class Client {
    @Id
    @Column
    private UUID id;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @Column
    private String passportID;

    public Client(){}

    public Client(UUID id, String firstName, String secondName, String phoneNumber, String email, String passportID) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportID = passportID;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }
}
