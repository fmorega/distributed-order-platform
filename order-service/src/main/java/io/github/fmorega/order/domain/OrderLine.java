package io.github.fmorega.order.domain;

public record OrderLine(String sku, int quantity) {

  public OrderLine {
    if (sku == null || sku.isBlank()) {
      throw new IllegalArgumentException("SKU must not be blank");
    }

    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }
  }
}
