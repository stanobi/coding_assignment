package com.daofab.coding_assignment.dto;

import com.daofab.coding_assignment.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuccessResponse extends ResponseHelper {

    public SuccessResponse(String message, Object data){
        super(true, message, data);
    }

    public SuccessResponse(Object data){
        super(true, Constants.SUCCESS_MESSAGE, data);
    }

}
