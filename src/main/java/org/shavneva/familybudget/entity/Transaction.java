package org.shavneva.familybudget.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "transaction")
@ToString(exclude = {"user", "category"})
public class Transaction extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtransaction")
    private int idtransaction;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_user"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcategory", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_category"))
    private Category category;

    @Column(name="currency", nullable = false)
    private String currency;
    @Pattern(regexp = "^-?\\d+(?:[.]\\d+)?$")
    @Column(name="amount", nullable = false)
    private String amount;
    @Column(name="date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    public Transaction(String currency, String amount) {
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public Integer getId() {
        return idtransaction;
    }
}
