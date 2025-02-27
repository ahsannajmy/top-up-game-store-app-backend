package com.example.top_up_store_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "app_user_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDetail extends Base{
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String fullname;
  @Column(name = "profile_image_url")
  private String profileImageUrl;
  @Column(name = "phone_number")
  private String phoneNumber;  

  @OneToOne
  @JoinColumn(name = "app_user_id")
  private AppUser user;
}