package me.yangsongi.electrozone.dto;

public record AddToCartRequest(
    Long productId,
    Integer quantity
) { }
