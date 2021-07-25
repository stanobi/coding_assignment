package com.daofab.coding_assignment.service;

import com.daofab.coding_assignment.dto.ChildDTO;
import com.daofab.coding_assignment.dto.ParentDTO;
import com.daofab.coding_assignment.repo.ChildRepo;
import com.daofab.coding_assignment.repo.ParentRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {

    @Spy
    ParentRepo parentRepo;

    @Spy
    ChildRepo childRepo;

    TransactionService transactionService;

    @BeforeEach
    void setUp() {

        transactionService = new TransactionService(parentRepo, childRepo);
        transactionService.parentFilePath = "classpath:Parent.json";
        transactionService.childFilePath = "classpath:Child.json";

        MockitoAnnotations.initMocks(this);
    }

    @Order(1)
    @Test
    void testLoadJsonIntoDatabase() {
        String result = transactionService.loadJsonIntoDatabase();
        Assertions.assertEquals(Strings.EMPTY, result);

        transactionService.parentFilePath = null;
        Assertions.assertNotEquals(Strings.EMPTY, transactionService.loadJsonIntoDatabase());

        transactionService.parentFilePath = "classpath:Parent.json";
        transactionService.childFilePath = null;
        Assertions.assertNotEquals(Strings.EMPTY, transactionService.loadJsonIntoDatabase());

    }

    @Order(2)
    @Test
    void testGetAllParent() {

        ParentDTO parentDTO = new ParentDTO(1L, "ABC", "XYZ", new BigDecimal("200"), new BigDecimal("20"));
        when(parentRepo.findAllParent(any(PageRequest.class))).thenReturn(new PageImpl<>(Collections.singletonList(parentDTO)));
        Page<ParentDTO> result = transactionService.getAllParent(0);
        log.info("result => {}", result);
        Assertions.assertAll("Checking content",
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertFalse(result.isEmpty()));

        when(parentRepo.findAllParent(any(PageRequest.class))).thenThrow(NullPointerException.class);
        Assertions.assertTrue(transactionService.getAllParent(0).isEmpty());
    }

    @Order(3)
    @Test
    void testGetAllChildByParentId() {

        Assertions.assertEquals(Strings.EMPTY, transactionService.loadJsonIntoDatabase());

        ChildDTO childDTO = new ChildDTO(1L, "ABC", "XYZ", new BigDecimal("200"), new BigDecimal("20"));
        when(childRepo.findAllByParentId(Mockito.anyLong())).thenReturn(Collections.singletonList(childDTO));

        List<ChildDTO> result = transactionService.getAllChildByParentId(1L);
        Assertions.assertAll("Checking content",
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertFalse(result.isEmpty()));

        when(childRepo.findAllByParentId(anyLong())).thenThrow(NullPointerException.class);
        Assertions.assertTrue(transactionService.getAllChildByParentId(1L).isEmpty());
    }
}