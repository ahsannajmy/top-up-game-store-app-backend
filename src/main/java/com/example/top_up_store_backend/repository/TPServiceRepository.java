package com.example.top_up_store_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.top_up_store_backend.models.TPService;

public interface TPServiceRepository extends JpaRepository<TPService, String>{
  
}
