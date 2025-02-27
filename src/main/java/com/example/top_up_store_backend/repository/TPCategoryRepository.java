package com.example.top_up_store_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.top_up_store_backend.models.TPCategory;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TPCategoryRepository extends JpaRepository<TPCategory, String> {
  @Query(
    value = 
    """
    SELECT * FROM tp_category
    LIMIT ?1 OFFSET ?2
    """,
    nativeQuery = true
  )
  List<TPCategory> pagingCategory(int limit, int offset);
  @Query(
    value = 
    """
    SELECT EXISTS (
      SELECT 1 FROM tp_category
      WHERE category_name ilike ?1
    )
    """,
    nativeQuery = true
  )
  Boolean checkCategoryName(String categoryName);
}

