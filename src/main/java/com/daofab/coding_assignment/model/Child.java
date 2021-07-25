package com.daofab.coding_assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Child {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parentId", nullable = false)
    private Parent parent;

    private BigDecimal paidAmount;

    public Child(Long id, Parent parent, BigDecimal paidAmount) {
        this.id = id;
        this.parent = parent;
        this.paidAmount = paidAmount;
    }
}
