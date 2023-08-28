package bank.system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "credit"
        ,schema = "PUBLIC"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Credit {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    private double limit;
    private double interestRate;

}
