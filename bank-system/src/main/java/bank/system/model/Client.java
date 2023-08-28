package bank.system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "client"
        ,schema = "PUBLIC"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue
    @UuidGenerator
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


}
