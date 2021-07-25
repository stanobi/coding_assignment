package com.daofab.coding_assignment.controller;

import com.daofab.coding_assignment.dto.ChildDTO;
import com.daofab.coding_assignment.dto.ErrorResponse;
import com.daofab.coding_assignment.dto.ParentDTO;
import com.daofab.coding_assignment.dto.ResponseHelper;
import com.daofab.coding_assignment.dto.SuccessResponse;
import com.daofab.coding_assignment.service.TransactionService;
import com.daofab.coding_assignment.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * This is the controller class
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.API_V1)
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/load-json")
    public ResponseEntity<ResponseHelper> loadJsonIntoDatabase(){
        String errorResponse = transactionService.loadJsonIntoDatabase();
        if (Strings.EMPTY.equals(errorResponse)){
            return ResponseEntity.ok(new SuccessResponse(errorResponse));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorResponse(errorResponse));
    }

    @GetMapping("/parents")
    public ResponseEntity<ResponseHelper> getAllParent(@RequestParam(required = false, defaultValue = "0") String pageNumber){
        int pageNum = 0;
        try {
            pageNum = Objects.isNull(pageNumber) ? 0 : Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            log.error("Invalid pageNumber provided. therefore using pageNum as 0 : ", e);
        }
        Page<ParentDTO> allParent = transactionService.getAllParent(pageNum);
        return ResponseEntity.ok(new SuccessResponse(allParent));
    }

    @GetMapping("/parents/{parentId}/child")
    public ResponseEntity<ResponseHelper> getAllChildByParentId(@PathVariable String parentId){
        long parentIdAsLong;
        try {
            parentIdAsLong = Long.parseLong(parentId);
        } catch (NumberFormatException e) {
            log.error("Invalid parentId provided ", e);
            return ResponseEntity.badRequest().body(new ErrorResponse());
        }

        List<ChildDTO> allChildByParentId = transactionService.getAllChildByParentId(parentIdAsLong);
        return ResponseEntity.ok(new SuccessResponse(allChildByParentId));
    }

}
