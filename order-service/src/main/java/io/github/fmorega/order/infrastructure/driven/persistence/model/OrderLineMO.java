package io.github.fmorega.order.infrastructure.driven.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderLineMO {

  @Column(name = "sku", nullable = false, length = 100)
  private String sku;

  @Column(name = "quantity", nullable = false)
  private int quantity;
}
