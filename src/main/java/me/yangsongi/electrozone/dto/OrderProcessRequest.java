package me.yangsongi.electrozone.dto;

public record OrderProcessRequest(
        String recipient,
        String address,
        String phone,
        Integer payment,
        String paymentMethod
) { }
