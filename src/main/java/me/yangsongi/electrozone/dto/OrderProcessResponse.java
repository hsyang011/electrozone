package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.User;

public record OrderProcessResponse(
    Long orderId,
    User user

) { }
