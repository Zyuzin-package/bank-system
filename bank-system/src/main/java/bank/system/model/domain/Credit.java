package bank.system.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "credit"
        ,schema = "PUBLIC"
)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credit {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    private double limit;
    private double interestRate;

}
