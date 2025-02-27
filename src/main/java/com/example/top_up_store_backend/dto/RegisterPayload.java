package com.example.top_up_store_backend.dto;

import com.example.top_up_store_backend.enums.Role;

public record RegisterPayload(String username, String email, String password, Role role) {
  
}
