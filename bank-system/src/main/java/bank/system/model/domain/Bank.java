package bank.system.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.UuidGenerator;


import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bank"
        , schema = "PUBLIC"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Bank {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    private String title;

    @OneToMany
    @Cascade(value ={CascadeType.REMOVE})
    private List<Credit> creditList;

    @OneToMany
    @Cascade(value ={CascadeType.REMOVE})
    private List<Client> clientList;

}
