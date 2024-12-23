package me.yangsongi.electrozone.dto;

public record OrderProcessRequest(
        String name,
        String address,
        String paymentMethod
) { }
