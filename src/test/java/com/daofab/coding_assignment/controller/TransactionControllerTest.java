package com.daofab.coding_assignment.controller;

import com.daofab.coding_assignment.dto.ChildDTO;
import com.daofab.coding_assignment.dto.ParentDTO;
import com.daofab.coding_assignment.dto.ResponseHelper;
import com.daofab.coding_assignment.service.TransactionService;
import com.daofab.coding_assignment.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void loadJsonIntoDatabase() throws Exception {

        String loadJsonUrl = "/api/v1/load-json";

        Mockito.when(transactionService.loadJsonIntoDatabase()).thenReturn(Strings.EMPTY);

        MockHttpServletResponse mockHttpServletResponse = getMockMvc(MockMvcRequestBuilders.get(loadJsonUrl));

        Assertions.assertAll("Confirming Success Response",
                () -> Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus()),
                () -> Assertions.assertEquals(Constants.SUCCESS_MESSAGE, new ObjectMapper().readValue(mockHttpServletResponse.getContentAsString(), ResponseHelper.class).getMessage()));

        Mockito.when(transactionService.loadJsonIntoDatabase()).thenReturn(Constants.ERROR_MESSAGE);

        MockHttpServletResponse mockHttpServletResponse2 = getMockMvc(MockMvcRequestBuilders.get(loadJsonUrl));

        Assertions.assertAll("Confirming Error Response",
                () -> Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED.value(), mockHttpServletResponse2.getStatus()),
                () -> Assertions.assertEquals(Constants.ERROR_MESSAGE, new ObjectMapper().readValue(mockHttpServletResponse2.getContentAsString(), ResponseHelper.class).getMessage()));

    }

    @Test
    void getAllParent() throws Exception {

        String loadParentUrl = "/api/v1/parents";

        ParentDTO parentDTO = new ParentDTO(1L, "ABC", "XYZ", new BigDecimal("200"), new BigDecimal("20"));
        Mockito.when(transactionService.getAllParent(Mockito.anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(parentDTO)));

        MockHttpServletResponse mockHttpServletResponse = getMockMvc(MockMvcRequestBuilders.get(loadParentUrl));

        ResponseHelper responseHelper = new ObjectMapper().readValue(mockHttpServletResponse.getContentAsString(), ResponseHelper.class);

        Assertions.assertAll("Confirming Response",
                () -> Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus()),
                () -> Assertions.assertEquals(Constants.SUCCESS_MESSAGE, responseHelper.getMessage()),
                () -> Assertions.assertFalse(((LinkedHashMap)responseHelper.getData()).isEmpty()));

        MockHttpServletResponse mockHttpServletResponse2 = getMockMvc(MockMvcRequestBuilders.get(loadParentUrl+"?pageNumber=testingErr"));
        Assertions.assertAll("Confirming Error Response",
                () -> Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse2.getStatus()),
                () -> Assertions.assertFalse(((LinkedHashMap)new ObjectMapper().readValue(mockHttpServletResponse2.getContentAsString(), ResponseHelper.class).getData()).isEmpty()));

    }

    @Test
    void getAllChildByParentId() throws Exception {

        String loadChildUrl = "/api/v1/parents/{}/child";

        ChildDTO childDTO = new ChildDTO(1L, "ABC", "XYZ", new BigDecimal("200"), new BigDecimal("20"));
        Mockito.when(transactionService.getAllChildByParentId(Mockito.anyLong())).thenReturn(Collections.singletonList(childDTO));

        MockHttpServletResponse mockHttpServletResponse = getMockMvc(MockMvcRequestBuilders.get(loadChildUrl.replace("{}", "1")));

        ResponseHelper responseHelper = new ObjectMapper().readValue(mockHttpServletResponse.getContentAsString(), ResponseHelper.class);

        Assertions.assertAll("Confirming Response",
                () -> Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus()),
                () -> Assertions.assertEquals(Constants.SUCCESS_MESSAGE, responseHelper.getMessage()),
                () -> Assertions.assertFalse(((ArrayList)responseHelper.getData()).isEmpty()));

        MockHttpServletResponse mockHttpServletResponse2 = getMockMvc(MockMvcRequestBuilders.get(loadChildUrl.replace("{}", "testingErr")));
        Assertions.assertAll("Confirming Error Response",
                () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mockHttpServletResponse2.getStatus()),
                () -> Assertions.assertEquals(Constants.ERROR_MESSAGE, new ObjectMapper().readValue(mockHttpServletResponse2.getContentAsString(), ResponseHelper.class).getMessage()));

    }

    private MockHttpServletResponse getMockMvc(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) throws Exception {
        return mockMvc.perform(mockHttpServletRequestBuilder
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
    }
}