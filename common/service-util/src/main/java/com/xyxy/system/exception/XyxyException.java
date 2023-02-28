package com.xyxy.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XyxyException extends RuntimeException{

    private Integer code;
    private String msg;
}
