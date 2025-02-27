package com.example.top_up_store_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.top_up_store_backend.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByUsername(String username);

  @Query(
    value = 
    """
    SELECT EXISTS (
      SELECT 1 FROM app_user
      WHERE username = ?1 and email = ?2
    )
    """,
    nativeQuery = true
  )
  Boolean checkExistingEmailAndUsername(String username, String email);
}

