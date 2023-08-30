package bank.system.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

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
@ToString
public class PaymentEvent {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;
    @OneToOne
    private CreditOffer creditOffer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;

    private double paymentSum;

    private double creditSum;

    private double interestSum;

}
