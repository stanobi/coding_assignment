package com.daofab.coding_assignment.dto;

import com.daofab.coding_assignment.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
public class ErrorResponse extends ResponseHelper {

    public ErrorResponse(String message){
        super(false, message, Strings.EMPTY);
    }

    public ErrorResponse(){
        super(false, Constants.ERROR_MESSAGE, Strings.EMPTY);
    }

}