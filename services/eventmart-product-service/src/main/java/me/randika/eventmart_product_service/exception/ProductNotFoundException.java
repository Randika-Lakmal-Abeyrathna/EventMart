package me.randika.eventmart_product_service.exception;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(String message) {
        super(message,"PRODUCT_NOT_FOUND");
    }
}
