package com.daofab.coding_assignment.repo;

import com.daofab.coding_assignment.model.Parent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ParentRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParentRepo parentRepo;

    @Test
    void findAllParent() {

        Parent parent1 = new Parent(1L, "ABC", "XYZ", new BigDecimal("200"));
        Parent parent2 = new Parent(2L, "XYZ", "MNP", new BigDecimal("100"));
        Parent parent3 = new Parent(3L, "XYZ", "MNP", new BigDecimal("300"));

        entityManager.persist(parent1);
        entityManager.persist(parent2);
        entityManager.persist(parent3);
        entityManager.flush();
        List<Parent> parentList = parentRepo.findAll();

        Assertions.assertAll("checking content",
                () -> Assertions.assertFalse(parentList.isEmpty()),
                () -> Assertions.assertEquals(3, parentList.size()),
                () -> Assertions.assertEquals(1L, parentList.get(0).getId()));
    }
}