package me.yangsongi.electrozone.dto;

import me.yangsongi.electrozone.domain.User;

import java.util.List;

public record MyPageViewResponse(
    User user,
    List<OrderViewResponse> orders
) { }
