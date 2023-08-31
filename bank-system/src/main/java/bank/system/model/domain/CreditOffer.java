package bank.system.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
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
@ToString
@NamedEntityGraph(name = "credit_entity-graph",
        attributeNodes = { @NamedAttributeNode("paymentEventList"),
                           @NamedAttributeNode("credit")})
public class CreditOffer {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    @OneToOne
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    private Credit credit;

    @OneToMany
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<PaymentEvent> paymentEventList;
}
