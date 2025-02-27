package com.example.top_up_store_backend.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tp_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TPCategory extends Base {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true)
  private String categoryName;

  @OneToMany(mappedBy = "tpCategory")
  private Set<TPService> tpServices;
}
