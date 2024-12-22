package com.toy.namoner.common.exceptions;

import com.toy.namoner.common.exceptions.dto.BadRequestBodyDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(CommonBadRequestException.class)
    public ResponseEntity<BadRequestBodyDto> commonBadRequestException(CommonBadRequestException exception, HttpServletRequest request) {
        CommonResponseCode responseCode = exception.getResponseCode();

        BadRequestBodyDto responseBody = BadRequestBodyDto.builder()
                .code(responseCode.getCode())
                .description(responseCode.getDescription())
                .detailDescription(exception.getDetailDescription())
                .build();

        log.info("Bad Request! IN {} {} \n" +
                "CODE : {} \n" +
                "DESCRIPTION: {} \n" +
                "DETAIL_DESCRIPTION: {}",
                request.getMethod(), request.getRequestURI(),
                responseBody.getCode(), responseBody.getDescription(), responseBody.getDetailDescription()
        );

        return ResponseEntity.badRequest().body(responseBody);
    }
}
