package com.daofab.coding_assignment.repo;

import com.daofab.coding_assignment.dto.ChildDTO;
import com.daofab.coding_assignment.model.Child;
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
class ChildRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChildRepo childRepo;

    @Test
    void findAllByParentId() {

        Parent parent = new Parent(1L, "ABC", "XYZ", new BigDecimal("200"));
        entityManager.persist(parent);
        entityManager.persist(new Child(1L, parent, new BigDecimal("50")));
        entityManager.persist(new Child(2L, parent, new BigDecimal("50")));
        entityManager.persist(new Child(3L, parent, new BigDecimal("100")));
        entityManager.flush();

        List<ChildDTO> childDTOList = childRepo.findAllByParentId(parent.getId());
        Assertions.assertAll("checking content",
                () -> Assertions.assertNotNull(childDTOList),
                () -> Assertions.assertFalse(childDTOList.isEmpty()),
                () -> Assertions.assertEquals(3, childDTOList.size()));

        Assertions.assertTrue(childRepo.findAllByParentId(0L).isEmpty());
    }
}