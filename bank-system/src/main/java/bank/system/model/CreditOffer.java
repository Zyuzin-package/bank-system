package bank.system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit_offer"
        , schema = "PUBLIC"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreditOffer {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    @OneToOne
    private Client client;

    @OneToOne
    private Credit credit;

    @OneToMany
    private List<PaymentEvent> paymentEventList;
}
