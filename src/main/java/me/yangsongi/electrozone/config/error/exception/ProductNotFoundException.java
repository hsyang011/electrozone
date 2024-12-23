package me.yangsongi.electrozone.config.error.exception;

import me.yangsongi.electrozone.config.error.ErrorCode;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }

}