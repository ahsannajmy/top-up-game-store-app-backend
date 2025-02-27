package com.example.top_up_store_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tp_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TPItem extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private int itemNominal;
  private String itemName;
  private String itemImageUrl;
  
  @ManyToOne
  @JoinColumn(name = "tp_service_id", nullable = false)
  private TPService tpService;
}
