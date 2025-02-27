package com.example.top_up_store_backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.top_up_store_backend.dto.Pagination;

public class ResponseFormat {
  public static <T> ResponseEntity<ApiResponse<T>> generateSuccessResponse(String message, T data, HttpStatus status) {    
    ApiResponse<T> response = new ApiResponse<T>(true, message, data, null);
    return new ResponseEntity<>(response, status);
  }
  
  public static <T> ResponseEntity<ApiResponse<T>> generateSuccessResponse(String message, T data, HttpStatus status, Pagination pagination) {
    ApiResponse<T> response = new ApiResponse<T>(true, message, data, pagination, null);
    return new ResponseEntity<>(response, status);
  }

  public static <T> ResponseEntity<ApiResponse<T>> generateErrorResponse(String message, Object errors, HttpStatus status) {
    ApiResponse<T> response = new ApiResponse<T>(false, message, null, errors);
    return new ResponseEntity<>(response, status);
  }
}
