package com.example.top_up_store_backend.utils;

import com.example.top_up_store_backend.dto.Pagination;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private Boolean success;
  private String message;
  private T data;
  private Object error;
  private Pagination pagination;

  public ApiResponse(Boolean success, String message, T data, Object error) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.error = error;
  }

  
  public ApiResponse(Boolean success, String message, T data, Pagination pagination, Object error) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.pagination = pagination;
    this.error = error;    
  }
  
  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }
  public Boolean getSuccess() {
    return success;
  }
  public void setSuccess(Boolean success) {
    this.success = success;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public T getData() {
    return data;
  }
  public void setData(T data) {
    this.data = data;
  }
  public Object getError() {
    return error;
  }
  public void setError(Object error) {
    this.error = error;
  }

  
}
