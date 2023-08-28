package bank.system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment_event"
        , schema = "PUBLIC"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaymentEvent {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    private LocalDate localDate;
    private double paymentSum;
    private double creditSum;
    private double interestSum;
}
