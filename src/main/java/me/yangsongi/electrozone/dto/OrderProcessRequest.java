package me.yangsongi.electrozone.dto;

public record OrderProcessRequest(
        String recipient,
        String address,
        String phone,
        String paymentMethod
) { }
