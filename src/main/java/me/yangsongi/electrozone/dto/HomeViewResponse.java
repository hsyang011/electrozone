package me.yangsongi.electrozone.dto;

public record HomeViewResponse(
    Long productId,
    String imageUrl,
    String name,
    Integer price
) { }
