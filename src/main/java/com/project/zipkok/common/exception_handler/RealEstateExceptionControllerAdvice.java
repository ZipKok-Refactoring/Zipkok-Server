package com.project.zipkok.common.exception_handler;

import com.project.zipkok.common.exception.RealEstateException;
import com.project.zipkok.common.response.BaseExceptionResponse;
import com.project.zipkok.common.response.FieldErrorDetail;
import com.project.zipkok.common.response.ValidationErrorResponse;
import com.project.zipkok.common.response.status.BaseExceptionResponseStatus;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class RealEstateExceptionControllerAdvice {

    private static final Map<String, BaseExceptionResponseStatus> FIELD_ERROR_MAP = createFieldErrorMap();

    private static Map<String, BaseExceptionResponseStatus> createFieldErrorMap() {
        Map<String, BaseExceptionResponseStatus> fieldErrorMap = new HashMap<>();
        // 매물 직접 등록 api에 대한 field
        fieldErrorMap.put("realEstateName", INVALID_PROPERTY_NAME);
        fieldErrorMap.put("transactionType", INVALID_RENTAL_PRICE_FORMAT);
        fieldErrorMap.put("realEstateType", INVALID_ESTATE_TYPE_FORMAT);
        fieldErrorMap.put("administrativeFee", INVALID_MANAGEMENT_FEE_FORMAT);
        fieldErrorMap.put("address", INVALID_ADDRESS_FORMAT);
        fieldErrorMap.put("latitude", INVALID_LATITUDE_FORMAT);
        fieldErrorMap.put("longitude", INVALID_LONGITUDE_FORMAT);

        //홈 화면 지도 API 에 대한 field
        fieldErrorMap.put("southWestLat", INVALID_LATITUDE_FORMAT);
        fieldErrorMap.put("northEastLat", INVALID_LATITUDE_FORMAT);
        fieldErrorMap.put("southWestLon", INVALID_LONGITUDE_FORMAT);
        fieldErrorMap.put("northEastLon", INVALID_LONGITUDE_FORMAT);
        fieldErrorMap.put("smallerThan", MIN_POINT_IS_BIGGER_THAN_MAX_POINT);

        return fieldErrorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RealEstateException.class)
    public BaseExceptionResponse handle_RealEstateException(RealEstateException e) {
        log.error("[handle_RealEstateException]", e);
        return new BaseExceptionResponse(e.getResponseStatus(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handle_UserValidationException(MethodArgumentNotValidException e) {
        log.error("[handle_UserValidationException]", e);
        List<FieldErrorDetail> fieldErrors = processFieldErrors(e);
        fieldErrors.forEach(fieldErrorDetail -> {
            log.info("[fieldName, message, code] {} {} {}", fieldErrorDetail.getField(), fieldErrorDetail.getCode(), fieldErrorDetail.getMessage());
        });

        return new ValidationErrorResponse(INVALID_FIELD_FORMAT, fieldErrors);
    }

    private List<FieldErrorDetail> processFieldErrors(MethodArgumentNotValidException e) {
        List<FieldErrorDetail> fieldErrors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            log.info("[processFieldName] {}", fieldName);
            BaseExceptionResponseStatus errorStatus = FIELD_ERROR_MAP.getOrDefault(fieldName, INVALID_FIELD_FORMAT);
            fieldErrors.add(new FieldErrorDetail(fieldName, errorStatus.getCode(), errorStatus.getMessage()));
        });
        return fieldErrors;
    }
}
