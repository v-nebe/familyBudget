package org.shavneva.familybudget.entity;

import jakarta.persistence.*;
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
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtransaction")
    private int idtransaction;

    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "idcategory", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_category"))
    private Category category;

    @Column(name="currency", nullable = false)
    private String currency;
    @Column(name="amount", nullable = false)
    public String amount;
    @Column(name="date", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date date;
}
