package org.shavneva.familybudget.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "category",
        uniqueConstraints = { @UniqueConstraint(columnNames = "categoryname") }
    )
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategory")
    private int idcategory;
    @Column(name = "categoryname", nullable = false, unique = true)
    private String categoryname;
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @Override
    public String toString() {
        return categoryname;
    }
}
