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
    public Credit(UUID id, double limit, double interestRate) {
        this.id = id;
        this.limit = limit;
        this.interestRate = interestRate;
    }
}
