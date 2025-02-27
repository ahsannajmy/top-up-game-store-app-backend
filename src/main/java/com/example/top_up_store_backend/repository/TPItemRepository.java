package com.example.top_up_store_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.top_up_store_backend.models.TPItem;

public interface TPItemRepository extends JpaRepository<TPItem, String> {
  
}
