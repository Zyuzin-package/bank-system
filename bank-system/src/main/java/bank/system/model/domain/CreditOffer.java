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
@NamedEntityGraph(name = "credit_entity-graph",
        attributeNodes = {@NamedAttributeNode("paymentEventList"),
                @NamedAttributeNode("credit")})
public class CreditOffer {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Client client;
    private double paymentSum;
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Credit credit;
    private int duration;
    @OneToMany
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<PaymentEvent> paymentEventList;

    @Override
    public String toString() {
        return "CreditOffer{" +
                "id=" + id +
                ", client=" + client +
                ", paymentSum=" + paymentSum +
                ", credit=" + credit +
                ", duration=" + duration +
                ", paymentEventList=" + paymentEventList +
                '}';
    }
}
