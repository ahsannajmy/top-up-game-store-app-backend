package com.example.top_up_store_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.top_up_store_backend.dto.Pagination;
import com.example.top_up_store_backend.dto.TPCategoryPayload;
import com.example.top_up_store_backend.models.TPCategory;
import com.example.top_up_store_backend.repository.TPCategoryRepository;
import com.example.top_up_store_backend.utils.ApiResponse;
import com.example.top_up_store_backend.utils.ResponseFormat;

@RestController
@RequestMapping("/api/admin/tp-category")
public class TPCategoryController {

  @Autowired
  private TPCategoryRepository tpCategoryRepository;

  @GetMapping("")
  public ResponseEntity<ApiResponse<Object>> allCategory(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int perPage) {
    try {      
      List<TPCategory> categories = tpCategoryRepository.pagingCategory(perPage, (page - 1) * perPage); 
      int totalRows = tpCategoryRepository.findAll().size();   
      int totalPages = Math.ceilDiv(totalRows, perPage);  
      Pagination pagination = new Pagination(perPage, page, totalRows, totalPages);   
      return ResponseFormat.generateSuccessResponse("Categories data retrieved successfully", categories, HttpStatus.OK, pagination);          
    } catch (Exception e) {
      return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> allCategoryById(@PathVariable("id") String id) {
    try {
      TPCategory category = tpCategoryRepository.findById(id).orElse(null);
      if (category == null) {
        return ResponseFormat.generateSuccessResponse("Category not found", null , HttpStatus.NOT_FOUND);
      }
      return ResponseFormat.generateSuccessResponse("Category data retrieved successfully", category, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("")
  public ResponseEntity<ApiResponse<Object>> addNewCategory(@RequestBody TPCategoryPayload category) {
    try {
      TPCategory tpCategory = new TPCategory();      
      if (tpCategoryRepository.checkCategoryName(category.categoryName())) {
        return ResponseFormat.generateErrorResponse(category.categoryName() + " already exist", null, HttpStatus.CONFLICT);
      }
      tpCategory.setCategoryName(category.categoryName());   
      TPCategory savedTPCategory = tpCategoryRepository.save(tpCategory);   
      return ResponseFormat.generateSuccessResponse("Category successfully created", savedTPCategory , HttpStatus.CREATED);
    } catch (Exception e) {      
      return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> editCategory(@RequestBody TPCategoryPayload category, @PathVariable("id") String tpCategoryId) {
    try {
      TPCategory tpCategory = tpCategoryRepository.findById(tpCategoryId).orElse(null);
      if (tpCategory == null) {
        return ResponseFormat.generateErrorResponse("Category not found", null, HttpStatus.NOT_FOUND);
      }   
      tpCategory.setCategoryName(category.categoryName());
      tpCategoryRepository.save(tpCategory);   
      return ResponseFormat.generateSuccessResponse("Category successfully updated", tpCategory, HttpStatus.OK);
    } catch (Exception e) {      
      return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable("id") String tpCategoryId) {
    try {
      TPCategory tpCategory = tpCategoryRepository.findById(tpCategoryId).orElse(null);
      if (tpCategory == null) {
        return ResponseFormat.generateErrorResponse("Category not found", null, HttpStatus.NOT_FOUND);
      }
      tpCategoryRepository.delete(tpCategory);
      return ResponseFormat.generateSuccessResponse("Cateogry successfully deleted", tpCategory,  HttpStatus.OK);
    } catch (Exception e) {
      return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }  

}
