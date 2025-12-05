package me.randika.eventmart_product_service.exception;

public class DuplicateProductCodeException extends BaseException {
    public DuplicateProductCodeException(String message) {
        super(message,"DUPLICATE_PRODUCT_CODE");
    }
}
