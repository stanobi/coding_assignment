package com.daofab.coding_assignment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Parent {

    @Id
    private Long id;
    private String sender;
    private String receiver;
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Child> child;

    public Parent(Long id) {
        this.id = id;
    }

    public Parent(Long id, String sender, String receiver, BigDecimal totalAmount) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.totalAmount = totalAmount;
    }
}
