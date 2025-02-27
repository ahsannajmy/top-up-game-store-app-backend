package com.example.top_up_store_backend.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tp_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TPService extends Base {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "service_name")
  private String serviceName;
  @Column(name = "service_owner")
  private String serviceOwner;
  @Column(name = "service_image_url")
  private String serviceImageUrl;

  @ManyToOne
  @JoinColumn(name = "tp_category_id", nullable = false)
  private TPCategory tpCategory;

  @OneToMany(mappedBy = "tpService")
  private Set<TPItem> tpItems;
}

