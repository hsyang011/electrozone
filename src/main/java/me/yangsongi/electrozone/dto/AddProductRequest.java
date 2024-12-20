package me.yangsongi.electrozone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record AddProductRequest(
        @JsonProperty("prod_id")
        Long productId, // JSON의 prod_id와 매핑

        String name,
        Integer price,
        List<String> specs,

        @JsonProperty("image_url")
        String imageUrl,

        @JsonProperty("registration_date")
        String registeredAt
) { }
