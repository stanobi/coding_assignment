package com.daofab.coding_assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseHelper {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+1")
    private Date timeStamp = new Date();
    private Boolean status;
    private String message;
    private Object data;

    public ResponseHelper(Boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}